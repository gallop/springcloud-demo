package com.gallop.springcloud.consumerservice.exception;

import com.gallop.common.exception.auth.UserInvalidException;
import com.gallop.common.exception.auth.UserTokenException;
import com.gallop.common.utils.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author gallop
 * date 2020-05-26 21:20
 * Description:
 * Modified By:
 */
@ControllerAdvice
@Order( value = Ordered.HIGHEST_PRECEDENCE )
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(UserInvalidException.class)
    @ResponseBody
    public Object userInvalidExceptionHandler(UserInvalidException e) {
        e.printStackTrace();
        log.info("进入userInvalidExceptionHandler。。。。。。");
        log.error(e.getMessage());
        return JSONResult.errorMsg(e.getStatus(),e.getMessage());
    }

    @ExceptionHandler(UserTokenException.class)
    @ResponseBody
    public Object userTokenExceptionHandler(UserTokenException e) {
        e.printStackTrace();
        log.info("进入userTokenExceptionHandler。。。。。。");
        log.error(e.getMessage());
        return JSONResult.errorMsg(e.getStatus(),e.getMessage());
    }
}
