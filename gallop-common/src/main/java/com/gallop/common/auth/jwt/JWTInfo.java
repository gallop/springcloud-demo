package com.gallop.common.auth.jwt;

import java.io.Serializable;

/**
 * author gallop
 * date 2020-05-26 20:15
 * Description:
 * Modified By:
 */
public class JWTInfo implements Serializable,IJWTInfo {
    private static final long serialVersionUID = 668619385834678030L;

    private String username;
    private String userId;
    private String mchId;

    public JWTInfo(String username, String userId, String mchId) {
        this.username = username;
        this.userId = userId;
        this.mchId = mchId;
    }

    @Override
    public String getUniqueName() {
        return username;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getMchId() {
        return mchId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String mchId) {
        this.mchId = mchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JWTInfo jwtInfo = (JWTInfo) o;

        if (username != null ? !username.equals(jwtInfo.username) : jwtInfo.username != null) {
            return false;
        }
        return userId != null ? userId.equals(jwtInfo.userId) : jwtInfo.userId == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JWTInfo{" +
                "username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", mchId='" + mchId + '\'' +
                '}';
    }
}
