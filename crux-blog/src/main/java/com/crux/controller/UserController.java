package com.crux.controller;

import com.crux.annotation.SystemLog;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.User;
import com.crux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author crucistau
 **/
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "获取用户信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }


    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }


    @PostMapping("/register")
    @SystemLog(businessName = "注册")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }


}
