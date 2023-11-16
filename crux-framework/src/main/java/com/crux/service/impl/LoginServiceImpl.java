package com.crux.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.constants.RedisConstants;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.LoginUser;
import com.crux.entity.domain.entity.User;
import com.crux.entity.vo.BlogUserLoginVo;
import com.crux.entity.vo.UserInfoVo;
import com.crux.mapper.UserMapper;


import com.crux.service.LoginService;
import com.crux.utils.BeanCopyUtils;
import com.crux.utils.JwtUtil;
import com.crux.utils.RedisCache;
import com.crux.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author crucistau
 **/
@Service
public class LoginServiceImpl  extends ServiceImpl<UserMapper, User> implements LoginService {

    //通过配置类securityConfig注入到容器中
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //封装成authentication
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //该方法会调用我们实现UserService的UserDetailServiceImpl方法，去数据库中查询信息
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        redisCache.setCacheObject(RedisConstants.ADMIN_LOGIN + userId,loginUser);

        //将token和userinfo信息封装返回
        //把user转换成userinfo返回
        UserInfoVo userInfoVo =
                BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logOut() {
        //获取token并解析获取userId（同一个线程只能拿到他自己的数据）
        Long userId = SecurityUtils.getUserId();
        //删除redis中的用户信息
        redisCache.deleteObject(RedisConstants.ADMIN_LOGIN + userId);
        return ResponseResult.okResult();
    }
}
