package com.crux.controller;

import com.alibaba.excel.util.StringUtils;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.User;
import com.crux.enums.AppHttpCodeEnum;
import com.crux.exception.SystemException;
import com.crux.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author crucistau
 **/
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        System.out.println(user);
        if (StringUtils.isEmpty(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/logOut")
    public ResponseResult logOut(){
        return loginService.logOut();
    }
}
