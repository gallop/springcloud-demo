package com.gallop.springcloud.consumerservice.controller;

import com.gallop.common.utils.JSONResult;
import com.gallop.springcloud.consumerservice.pojo.User;
import com.gallop.springcloud.consumerservice.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * author gallop
 * date 2020-05-26 12:52
 * Description:
 * Modified By:
 */
@RestController
@RequestMapping("/auth/jwt")
@Slf4j
public class AuthController {
    @Value("${jwt.token-header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public Object login(@RequestBody User user) throws Exception{
        log.info(user.getUsername()+" require logging...");
        final String token = authService.login(user);
        user.setUserToken(token);
        return JSONResult.ok(user);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public Object refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws Exception {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", refreshedToken);
        return JSONResult.ok(result);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public Object verify(String token) throws Exception {
        authService.validate(token);
        return JSONResult.ok();
    }
}
