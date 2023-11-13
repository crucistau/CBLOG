package com.crux.service;

import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.User;

/**
 * @author crucistau
 **/
public interface LoginService {
    ResponseResult login(User user);

}
