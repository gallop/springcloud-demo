package com.gallop.common.auth.jwt;

import com.gallop.common.constatns.JwtKeyConstants;
import com.gallop.common.utils.DateTimeUtil;
import com.gallop.common.utils.StringHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Created by ace on 2017/9/10.
 */
public class JWTHelper {
    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();
    /**
     * 密钥加密token
     *
     * @param jwtInfo
     * @param priKeyPath
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(IJWTInfo jwtInfo, String priKeyPath, int expire) throws Exception {
        // 过期时间：2小时
        Date expireDate = DateTimeUtil.getAfterDate(new Date(), 0, 0, 0, 2, 0, 0);
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getUniqueName())
                .claim(JwtKeyConstants.JWT_KEY_USER_ID, jwtInfo.getUserId())
                .claim(JwtKeyConstants.JWT_KEY_MCH_ID, jwtInfo.getMchId())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyPath))
                .compact();
        return compactJws;
    }

    /**
     * 密钥加密token
     *
     * @param jwtInfo
     * @param priKey
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(IJWTInfo jwtInfo, byte priKey[], int expire) throws Exception {
        // 过期时间：2小时
        Date expireDate = DateTimeUtil.getAfterDate(new Date(), 0, 0, 0, 2, 0, 0);
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getUniqueName())
                .claim(JwtKeyConstants.JWT_KEY_USER_ID, jwtInfo.getUserId())
                .claim(JwtKeyConstants.JWT_KEY_MCH_ID, jwtInfo.getMchId())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath)).parseClaimsJws(token);
        return claimsJws;
    }
    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token);
        return claimsJws;
    }
    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKeyPath
     * @return
     * @throws Exception
     */
    public static IJWTInfo getInfoFromToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(), StringHelper.getObjectValue(body.get(JwtKeyConstants.JWT_KEY_USER_ID)), StringHelper.getObjectValue(body.get(JwtKeyConstants.JWT_KEY_MCH_ID)));
    }
    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static IJWTInfo getInfoFromToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(), StringHelper.getObjectValue(body.get(JwtKeyConstants.JWT_KEY_USER_ID)), StringHelper.getObjectValue(body.get(JwtKeyConstants.JWT_KEY_MCH_ID)));
    }
}
