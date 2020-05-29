package com.gallop.common.exception.auth;

import com.gallop.common.constatns.ResponseCode;
import com.gallop.common.exception.BaseException;

/**
 * Created by ace on 2017/9/8.
 */
public class UserInvalidException extends BaseException {
    public UserInvalidException(String message) {
        super(message, ResponseCode.EX_USER_INVALID_TOKEN);
    }
}
