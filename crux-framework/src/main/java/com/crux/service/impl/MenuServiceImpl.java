package com.crux.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.entity.domain.entity.Menu;
import com.crux.mapper.MenuMapper;
import com.crux.service.MenuService;
import com.crux.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author crucistau
 * @since 2023-11-13 16:50:29
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是超级管理员返回所有权限
        if (id == 1L){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, "C","F");
            List<Menu> list = list(queryWrapper);
            List<String> perms = list.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则查询该用户所具有的权限信息
        return getBaseMapper().selectParamsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()){
            //如果是，取出所有符合要求的menu
             menus = menuMapper.selectAllRouterMenu();
        }else{
            //如果不是则获取当前用户的menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找到第一层的菜单，然后将他们的子菜单设置到children


        return null;
    }
    private List<Menu> buildMergeTree(List<Menu> menus, Long parentId){

        return null;
    }
}
