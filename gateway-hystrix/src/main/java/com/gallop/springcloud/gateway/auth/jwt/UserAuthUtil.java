package com.gallop.springcloud.gateway.auth.jwt;

import com.gallop.common.auth.jwt.IJWTInfo;
import com.gallop.common.auth.jwt.JWTHelper;
import com.gallop.common.exception.auth.UserTokenException;
import com.gallop.springcloud.gateway.config.UserJwtKeyConfig;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ace on 2017/9/15.
 */
@Configuration
@Slf4j
public class UserAuthUtil {
    @Autowired
    private UserJwtKeyConfig userJwtKeyConfig;
    public IJWTInfo getInfoFromToken(String token) throws UserTokenException {
        try {
            return JWTHelper.getInfoFromToken(token, userJwtKeyConfig.getPubKeyByte());
        }catch (ExpiredJwtException ex){
            throw new UserTokenException("User token expired!");
        }catch (SignatureException ex){
            throw new UserTokenException("User token signature error!");
        }catch (IllegalArgumentException ex){
            throw new UserTokenException("User token is null or empty!");
        }catch (Exception e){
            log.error("解析token出错了：",e);
            throw new UserTokenException("unkown error for User token !");
        }
    }
}
