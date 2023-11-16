package com.crux.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crux.entity.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author crucistau
 * @since 2023-11-13 16:55:11
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}
