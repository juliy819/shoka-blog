package com.juliy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.juliy.entity.Menu;
import com.juliy.mapper.MenuMapper;
import com.juliy.model.vo.MetaVO;
import com.juliy.model.vo.RouterVO;
import com.juliy.service.MenuService;
import com.juliy.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.juliy.constant.CommonConstant.*;


/**
 * 菜单服务接口实现类
 * @author juliy
 * @date 2023/3/13 11:57
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<Menu> listMenuTreeByUserId(int userId) {
        List<Menu> menus;
        if (SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus);
    }

    @Override
    public List<RouterVO> buildMenus(List<Menu> menus) {
        List<RouterVO> routers = new LinkedList<>();
        // 将Menu对象转为RouterVO
        for (Menu menu : menus) {
            RouterVO router = new RouterVO();
            router.setName(menu.getMenuName());
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setMeta(MetaVO.builder()
                    .title(menu.getMenuName())
                    .icon(menu.getIcon())
                    .hidden(menu.getIsHidden().equals(TRUE))
                    .build());

            List<Menu> cMenus = menu.getChildren();
            // 有子节点且是目录类型时
            if (!cMenus.isEmpty() && menu.getMenuType().equals(TYPE_DIR)) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 根据父节点的ID获取所有子节点
     * @param list 分类表
     * @return String
     */
    private List<Menu> getChildPerms(List<Menu> list) {
        List<Menu> returnList = new ArrayList<>();
        // 遍历得到所有根节点(即parentId为0),并递归添加其所有子节点
        for (Menu menu : list) {
            if (menu.getParentId().equals(PARENT_ID)) {
                recursionFn(list, menu);
                returnList.add(menu);
            }
        }
        return returnList;
    }

    /**
     * 递归找出所有子节点
     * @param list 菜单列表
     * @param m    父级菜单节点
     */
    private void recursionFn(List<Menu> list, Menu m) {
        List<Menu> childList = getChildList(list, m);
        m.setChildren(childList);
        // 再判断子节点是否还有其自身的子节点
        for (Menu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     * @param list 菜单列表
     * @param m    父级菜单节点
     */
    private List<Menu> getChildList(List<Menu> list, Menu m) {
        List<Menu> mList = new ArrayList<>();
        for (Menu n : list) {
            if (n.getParentId().equals(m.getId())) {
                mList.add(n);
            }
        }
        return mList;
    }

    /**
     * 判断是否有子节点
     * @param list 菜单列表
     * @param m    父级菜单节点
     */
    private boolean hasChild(List<Menu> list, Menu m) {
        return getChildList(list, m).size() > 0;
    }


    /**
     * 获取路由地址
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(Menu menu) {
        String routerPath = menu.getPath();
        // 一级目录
        if (menu.getParentId().equals(PARENT_ID) && menu.getMenuType().equals(TYPE_DIR)) {
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(Menu menu) {
        String component = LAYOUT;
        if (StrUtil.isNotEmpty(menu.getComponent())) {
            component = menu.getComponent();
        } else if (StrUtil.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为parent_view组件
     * <br>判断标准:父组件id为0且为菜单类型
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(Menu menu) {
        return menu.getParentId().equals(PARENT_ID) && menu.getMenuType().equals(TYPE_MENU);
    }

}
