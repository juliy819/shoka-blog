package com.juliy.satoken;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.juliy.mapper.MenuMapper;
import com.juliy.mapper.RoleMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 * @author juliy
 * @date 2023/3/15 15:39
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    private final MenuMapper menuMapper;

    private final RoleMapper roleMapper;

    public StpInterfaceImpl(MenuMapper menuMapper,
                            RoleMapper roleMapper) {
        this.menuMapper = menuMapper;
        this.roleMapper = roleMapper;
    }


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = new ArrayList<>();
        // 遍历角色列表，查询用户对应的角色拥有的权限
        for (String roleId : getRoleList(loginId, loginType)) {
            // 将不同角色所拥有的权限存到自定义session中
            SaSession roleSession = SaSessionCustomUtil.getSessionById("role-" + roleId);
            List<String> list;
            // 若为管理员则添加所有权限
            if (Long.parseLong(roleId) == 1) {
                list = roleSession.get("Permission_List", menuMapper::selectPermissionAll);
            } else {
                list = roleSession.get("Permission_List", () -> menuMapper.selectPermissionByRoleId(roleId));
            }
            permissionList.addAll(list);
        }
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get("Role_List", () -> roleMapper.selectRoleListByUserId(loginId));
    }
}
