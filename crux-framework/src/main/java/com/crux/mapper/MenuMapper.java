package com.crux.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crux.entity.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author crucistau
 * @since 2023-11-13 16:50:27
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectParamsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
