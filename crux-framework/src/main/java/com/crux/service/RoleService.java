package com.crux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.entity.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author crucistau
 * @since 2023-11-13 16:55:11
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
