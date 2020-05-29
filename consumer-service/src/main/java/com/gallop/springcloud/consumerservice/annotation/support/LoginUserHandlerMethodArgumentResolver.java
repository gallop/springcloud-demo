package com.gallop.springcloud.consumerservice.annotation.support;

import com.gallop.common.exception.auth.UserTokenException;
import com.gallop.springcloud.consumerservice.annotation.CurrentUser;
import com.gallop.springcloud.consumerservice.annotation.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * author gallop
 * date 2020-05-27 12:05
 * Description:
 * Modified By:
 */
@Slf4j
@Configuration
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String USER_ID = "user_id";
    private static final String MCH_ID = "mch_id";
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterType().isAssignableFrom(CurrentUser.class) && methodParameter.hasParameterAnnotation(LoginUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        String mchId = nativeWebRequest.getHeader(MCH_ID);
        String userId = nativeWebRequest.getHeader(USER_ID);
        log.info("userId:{}",userId);
        log.info("mchId:{}",mchId);
        if(!StringUtils.isEmpty(mchId) && !StringUtils.isEmpty(userId)){
            CurrentUser currentUser = new CurrentUser();
            currentUser.setMchId(Integer.parseInt(mchId));
            currentUser.setUserId(Integer.parseInt(userId));

            return currentUser;
        } else {
            throw new UserTokenException(" illegal user requests! ！！");
        }

    }
}
