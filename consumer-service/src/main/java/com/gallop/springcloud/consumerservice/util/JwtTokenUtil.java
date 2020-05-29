package com.gallop.springcloud.consumerservice.util;

import com.gallop.common.auth.jwt.IJWTInfo;
import com.gallop.common.auth.jwt.JWTHelper;
import com.gallop.common.exception.auth.UserTokenException;
import com.gallop.springcloud.consumerservice.config.JwtKeyConfiguration;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * author gallop
 * date 2020-05-26 21:05
 * Description:
 * Modified By:
 */
@Component
public class JwtTokenUtil {
    @Value("${jwt.expire}")
    private int expire;
    @Autowired
    private JwtKeyConfiguration jwtKeyConfiguration;


    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return JWTHelper.generateToken(jwtInfo, jwtKeyConfiguration.getUserPriKey(),expire);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, jwtKeyConfiguration.getUserPubKey());
        }catch (ExpiredJwtException ex){
            throw new UserTokenException("User token expired!");
        }catch (SignatureException ex){
            throw new UserTokenException("User token signature error!");
        }catch (IllegalArgumentException ex){
            throw new UserTokenException("User token is null or empty!");
        }
    }
}
