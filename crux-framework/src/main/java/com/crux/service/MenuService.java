package com.crux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.entity.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author crucistau
 * @since 2023-11-13 16:50:28
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
