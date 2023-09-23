package com.crux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.User;

/**
 * @author crucistau
 **/
public interface LoginService extends IService<User> {
    ResponseResult login(User user);

    ResponseResult logOut();
}
