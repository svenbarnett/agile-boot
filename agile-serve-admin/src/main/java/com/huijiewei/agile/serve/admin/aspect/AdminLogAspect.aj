package com.huijiewei.agile.serve.admin.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Log
public class AdminLogAspect {
    @Pointcut("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public void preAuthorize() {
    }

    @Pointcut("@annotation(com.huijiewei.agile.serve.admin.annotation.LoginLog)")
    public void login() {
    }

    @Around(value = "preAuthorize() || login()")
    public Object around(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();

            System.out.println(request.getMethod());
        }

        Object result = null;

        System.out.println("进入方法");

        log.info("进入方法");

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.info("方法报错");

            throwable.printStackTrace();
        }

        if (result != null) {
            log.info(result.toString());
        }

        log.info("完成方法");

        return result;
    }

}
