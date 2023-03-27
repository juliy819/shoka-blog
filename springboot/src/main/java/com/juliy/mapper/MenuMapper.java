package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单mapper
 * @author juliy
 * @date 2023/3/13 12:10
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    /**
     * 查询所有菜单
     * @return 菜单列表
     */
    List<Menu> selectMenuTreeAll();

    /**
     * 根据用户id查询菜单
     * @param userId 用户id
     * @return 菜单列表
     */
    List<Menu> selectMenuTreeByUserId(Integer userId);

    /**
     * 查询所有权限
     * @return 权限列表
     */
    List<String> selectPermissionAll();

    /**
     * 根据角色id查询对应权限
     * @param roleId id
     * @return 权限列表
     */
    List<String> selectPermissionByRoleId(String roleId);
}
