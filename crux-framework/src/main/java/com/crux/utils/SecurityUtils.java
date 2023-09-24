package com.crux.utils;

import com.crux.entity.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 通过SecurityContextHolder获得登录用户信息
 * @author crucistau
 **/
public class SecurityUtils {
    /**
     * 获取登录用户
     * @return
     */
    public static LoginUser getLoginUser(){
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     * @return
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取登录用户Id
     * @return
     */
    public static Long getUserId(){
        return getLoginUser().getUser().getId();
    }

    /**
     * 判断是否为管理员
     * @return
     */
    public static Boolean isAdmin(){
        Long userId = getUserId();
        return userId != null && userId == 1L;
    }

}
