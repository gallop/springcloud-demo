package com.gallop.springcloud.consumerservice.service.impl;

import com.gallop.common.auth.jwt.JWTInfo;
import com.gallop.common.exception.auth.UserInvalidException;
import com.gallop.springcloud.consumerservice.pojo.User;
import com.gallop.springcloud.consumerservice.service.AuthService;
import com.gallop.springcloud.consumerservice.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * author gallop
 * date 2020-05-26 20:49
 * Description:
 * Modified By:
 */
@Service
public class AuthServiceImpl implements AuthService {

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthServiceImpl(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public String login(User user) throws Exception {
        if(!user.getUsername().equals("gallop") || !user.getPassword().equals("123456")){
            throw new UserInvalidException("用户不存在或账户密码错误!");
        }
        user.setId(120001);
        user.setMchId("10001");
        return jwtTokenUtil.generateToken(new JWTInfo(user.getUsername(), user.getId() + "", user.getMchId()));
    }

    @Override
    public String refresh(String oldToken) throws Exception {
        return jwtTokenUtil.generateToken(jwtTokenUtil.getInfoFromToken(oldToken));
    }

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }
}
