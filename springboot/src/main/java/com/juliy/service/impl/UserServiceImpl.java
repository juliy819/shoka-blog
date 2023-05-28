package com.juliy.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.User;
import com.juliy.entity.UserRole;
import com.juliy.enums.FilePathEnum;
import com.juliy.mapper.RoleMapper;
import com.juliy.mapper.UserMapper;
import com.juliy.mapper.UserRoleMapper;
import com.juliy.model.dto.*;
import com.juliy.model.vo.*;
import com.juliy.service.FileService;
import com.juliy.service.RedisService;
import com.juliy.service.UserService;
import com.juliy.utils.CommonUtils;
import com.juliy.utils.PageUtils;
import com.juliy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.juliy.constant.CommonConstant.TRUE;
import static com.juliy.constant.RedisConstant.*;

/**
 * 用户服务接口实现类
 * @author juliy
 * @date 2023/3/15 17:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final RedisService redisService;
    private final FileService fileService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
                           RoleMapper roleMapper,
                           UserRoleMapper userRoleMapper,
                           RedisService redisService,
                           FileService fileService) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.redisService = redisService;
        this.fileService = fileService;
    }

    @Override
    public AdminUserInfoVO getAdminUserInfo() {
        // 获取登录用户id
        int userId = StpUtil.getLoginIdAsInt();
        // 获取用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                                                 .select(User::getAvatar)
                                                 .eq(User::getId, userId));
        List<String> roleList = StpUtil.getRoleList();
        // 去重、去空
        List<String> permissionList = StpUtil.getPermissionList()
                .stream()
                .filter(perm -> !StrUtil.isEmpty(perm))
                .distinct()
                .toList();
        //构建并返回
        return AdminUserInfoVO.builder()
                .id(userId)
                .avatar(user.getAvatar())
                .roleList(roleList)
                .permissionList(permissionList)
                .build();
    }

    @Override
    public UserInfoVO getUserInfo() {
        Integer userId = StpUtil.getLoginIdAsInt();
        User user = this.getOne(new LambdaQueryWrapper<User>()
                                        .select(User::getNickname, User::getAvatar, User::getUsername, User::getWebSite,
                                                User::getIntro, User::getEmail, User::getLoginType)
                                        .eq(User::getId, userId));
        Set<Object> articleLikeSet = redisService.getSet(ARTICLE_LIKE + userId);
        Set<Object> commentLikeSet = redisService.getSet(COMMENT_LIKE + userId);
        Set<Object> talkLikeSet = redisService.getSet(TALK_LIKE + userId);
        return UserInfoVO
                .builder()
                .id(userId)
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .email(user.getEmail())
                .webSite(user.getWebSite())
                .intro(user.getIntro())
                .articleLikeSet(articleLikeSet)
                .commentLikeSet(commentLikeSet)
                .talkLikeSet(talkLikeSet)
                .loginType(user.getLoginType())
                .build();
    }

    @Override
    public PageResult<UserAdminVO> listUserAdmin(ConditionDTO condition) {
        // 查询后台用户数量
        Long count = userMapper.countUser(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台用户列表
        List<UserAdminVO> userList = userMapper.selectUserAdmin(PageUtils.getLimitCurrent(),
                                                                PageUtils.getSize(), condition);
        return new PageResult<>(userList, count);
    }

    @Override
    public List<UserRoleVO> listUserRole() {
        return roleMapper.selectUserRoleList();
    }

    @Override
    public void updateUser(UserRoleDTO user) {
        // 更新用户信息
        User newUser = User.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
        baseMapper.updateById(newUser);
        // 删除用户角色
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        // 重新添加用户角色
        user.getRoleIdList().forEach(roleId -> userRoleMapper
                .insert(UserRole.builder()
                                .userId(user.getId())
                                .roleId(roleId)
                                .build()));
        // 删除Redis缓存中的角色
        SaSession sessionByLoginId = StpUtil.getSessionByLoginId(user.getId(), false);
        Optional.ofNullable(sessionByLoginId).ifPresent(saSession -> saSession.delete("Role_List"));
    }

    @Override
    public void updateUserStatus(DisableDTO disable) {
        // 更新用户状态
        User newUser = User.builder()
                .id(disable.getId())
                .isDisable(disable.getIsDisable())
                .build();
        userMapper.updateById(newUser);
        if (disable.getIsDisable().equals(TRUE)) {
            // 先踢下线再封禁
            StpUtil.logout(disable.getId());
            StpUtil.disable(disable.getId(), 86400);
        } else {
            // 解除封禁
            StpUtil.untieDisable(disable.getId());
        }
    }

    @Override
    public void updateUserEmail(EmailDTO email) {
        verifyCode(email.getEmail(), email.getCode());
        User user = User.builder()
                .id(StpUtil.getLoginIdAsInt())
                .email(email.getEmail())
                .build();
        userMapper.updateById(user);
    }

    @Override
    public String saveAvatar(MultipartFile file) {
        String avatar = fileService.saveFile(file, FilePathEnum.ARTICLE);
        User user = User.builder()
                .id(StpUtil.getLoginIdAsInt())
                .avatar(avatar)
                .build();
        this.updateById(user);
        return avatar;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserInfoDTO userInfo) {
        User user = User.builder()
                .id(StpUtil.getLoginIdAsInt())
                .nickname(userInfo.getNickname())
                .intro(userInfo.getIntro())
                .webSite(userInfo.getWebSite())
                .build();
        this.updateById(user);
    }

    @Override
    public void updatePassword(PasswordDTO password) {
        verifyCode(password.getUsername(), password.getCode());
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                                                      .select(User::getUsername)
                                                      .eq(User::getUsername, password.getUsername()));
        CommonUtils.checkParamNull(existUser, "邮箱尚未注册！");
        // 根据用户名修改密码
        this.update(new User(), new LambdaUpdateWrapper<User>()
                .set(User::getPassword, SecurityUtils.sha256Encrypt(password.getPassword()))
                .eq(User::getUsername, password.getUsername()));
    }

    /**
     * 校验验证码
     * @param username 用户名
     * @param code     验证码
     */
    public void verifyCode(String username, String code) {
        String sysCode = redisService.getObject(CODE_KEY + username);
        CommonUtils.checkParam(StrUtil.isBlank(code), "验证码未发送或已过期！");
        CommonUtils.checkParam(!sysCode.equals(code), "验证码错误，请重新输入！");
    }
}
