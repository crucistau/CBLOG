package com.crux.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.entity.domain.entity.Role;
import com.crux.mapper.RoleMapper;
import com.crux.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author crucistau
 * @since 2023-11-13 16:55:11
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员，如果是返回集合中只有admin
        if (id == 1L){
            ArrayList<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户对应的角色
        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}

