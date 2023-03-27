package com.juliy.service;

import com.juliy.entity.Menu;
import com.juliy.model.vo.RouterVO;

import java.util.List;

/**
 * 菜单服务接口
 * @author juliy
 * @date 2023/3/13 11:57
 */
public interface MenuService {

    /**
     * 根据用户ID查询菜单树信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<Menu> listMenuTreeByUserId(int userId);

    /**
     * 构建前端路由所需要的菜单
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVO> buildMenus(List<Menu> menus);
}
