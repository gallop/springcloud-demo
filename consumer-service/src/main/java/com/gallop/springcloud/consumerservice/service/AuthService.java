package com.gallop.springcloud.consumerservice.service;

import com.gallop.springcloud.consumerservice.pojo.User;

/**
 * author gallop
 * date 2020-05-26 20:48
 * Description:
 * Modified By:
 */
public interface AuthService {
    String login(User user) throws Exception;
    String refresh(String oldToken) throws Exception;
    void validate(String token) throws Exception;
}
