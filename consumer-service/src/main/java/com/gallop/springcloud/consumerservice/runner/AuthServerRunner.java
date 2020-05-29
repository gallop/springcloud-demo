package com.gallop.springcloud.consumerservice.runner;

import com.gallop.common.auth.jwt.RsaKeyHelper;
import com.gallop.springcloud.consumerservice.config.JwtKeyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * author gallop
 * date 2020-05-26 21:34
 * Description:
 * Modified By:
 */
@Component
public class AuthServerRunner implements CommandLineRunner {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String REDIS_USER_PRI_KEY = "AG:AUTH:JWT:PRI";
    private static final String REDIS_USER_PUB_KEY = "AG:AUTH:JWT:PUB";

    @Autowired
    private JwtKeyConfiguration jwtKeyConfiguration;

    @Override
    public void run(String... args) throws Exception {
        if (stringRedisTemplate.hasKey(REDIS_USER_PRI_KEY)&&stringRedisTemplate.hasKey(REDIS_USER_PUB_KEY)) {
            jwtKeyConfiguration.setUserPriKey(RsaKeyHelper.toBytes(stringRedisTemplate.opsForValue().get(REDIS_USER_PRI_KEY).toString()));
            jwtKeyConfiguration.setUserPubKey(RsaKeyHelper.toBytes(stringRedisTemplate.opsForValue().get(REDIS_USER_PUB_KEY).toString()));

        } else {
            Map<String, byte[]> keyMap = RsaKeyHelper.generateKey(jwtKeyConfiguration.getUserSecret());
            jwtKeyConfiguration.setUserPriKey(keyMap.get("pri"));
            jwtKeyConfiguration.setUserPubKey(keyMap.get("pub"));
            stringRedisTemplate.opsForValue().set(REDIS_USER_PRI_KEY, RsaKeyHelper.toHexString(keyMap.get("pri")));
            stringRedisTemplate.opsForValue().set(REDIS_USER_PUB_KEY, RsaKeyHelper.toHexString(keyMap.get("pub")));
        }
    }
}
