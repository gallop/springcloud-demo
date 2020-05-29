package com.gallop.common.auth.jwt;

/**
 * author gallop
 * date 2020-05-26 20:12
 * Description:
 * Modified By:
 */
public interface IJWTInfo {
    /**
     * 获取用户名
     * @return
     */
    String getUniqueName();

    /**
     * 获取用户ID
     * @return
     */
    String getUserId();

    /**
     * 获取所属商户ID
     * @return
     */
    String getMchId();
}
