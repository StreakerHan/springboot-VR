package com.streaker.utils;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常捕获类
 *
 * @author StreakerHan
 * @version 2018/9/11 10:22:32
 **/
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    public String handleAuthorizationException() {
        //System.out.println("**********调用异常捕捉类");
        return "403";
    }
}

