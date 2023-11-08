package com.crux.aspect;

import com.alibaba.fastjson.JSON;
import com.crux.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 切面类
 *
 * @author crucistau
 **/
@Component
@Aspect
@Slf4j
public class LogAspect {
    /**
     * 确定切点
     */

    @Pointcut("@annotation(com.crux.annotation.SystemLog)")
    public void pt() {

    }

    /**
     * 定义通知方法
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) {

        Object ret;
        try {
            handlerBefore(joinPoint);
            ret = joinPoint.proceed();
            handlerAfter(ret);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            // 结束后换行(换行符)
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;
    }

    private void handlerAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSON(ret));
    }

    private void handlerBefore(ProceedingJoinPoint joinPoint) {


        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringType(),
                ((MethodSignature)joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSON(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SystemLog annotation = signature.getMethod().getAnnotation(SystemLog.class);
        return annotation;
    }


}
