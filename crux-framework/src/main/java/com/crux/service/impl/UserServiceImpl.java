package com.crux.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.entity.domain.entity.User;
import com.crux.mapper.UserMapper;
import com.crux.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author crucistau
 * @since 2023-09-24 15:48:32
 */
@Service("sysUserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
