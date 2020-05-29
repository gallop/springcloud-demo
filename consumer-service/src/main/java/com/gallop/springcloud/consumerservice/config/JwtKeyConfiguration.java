package com.gallop.springcloud.consumerservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * author gallop
 * date 2020-05-26 21:01
 * Description:
 * Modified By:
 */
@Configuration
@Data
public class JwtKeyConfiguration {
    @Value("${jwt.rsa-secret}")
    private String userSecret;
    private byte[] userPubKey;
    private byte[] userPriKey;
}
