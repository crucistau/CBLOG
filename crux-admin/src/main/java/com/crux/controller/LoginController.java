package com.crux.controller;

import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.LoginUser;
import com.crux.entity.domain.entity.Menu;
import com.crux.entity.domain.entity.User;
import com.crux.entity.vo.AdminUserInfoVo;
import com.crux.entity.vo.BlogUserLoginVo;
import com.crux.entity.vo.RoutersVo;
import com.crux.entity.vo.UserInfoVo;
import com.crux.enums.AppHttpCodeEnum;
import com.crux.exception.SystemException;
import com.crux.service.LoginService;
import com.crux.service.MenuService;
import com.crux.service.RoleService;
import com.crux.utils.BeanCopyUtils;
import com.crux.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Security;
import java.util.List;

/**
 * @author crucistau
 **/
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        return loginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户Id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult();
    }


    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logOut();
    }
}
