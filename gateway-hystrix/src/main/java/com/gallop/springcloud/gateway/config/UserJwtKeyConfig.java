package com.gallop.springcloud.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * author gallop
 * date 2020-05-26 22:07
 * Description:
 * Modified By:
 */
@Data
@Component
public class UserJwtKeyConfig {
    @Value("${auth.user.token-header}")
    private String tokenHeader;
    @Value("${auth.client.token-header}")
    private String clientHeader;
    private byte[] pubKeyByte;

}
