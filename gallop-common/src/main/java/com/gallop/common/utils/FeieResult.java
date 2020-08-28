package com.gallop.common.utils;

import java.io.Serializable;

/**
 * author gallop
 * date 2020-06-03 22:28
 * Description:
 * Modified By:
 */
public class FeieResult<T> implements Serializable {
    private static final long serialVersionUID = -7129791233405857118L;

    private Integer ret;
    // 响应消息
    private String msg;
    // 响应中的数据
    private T data;

    public FeieResult() {
    }

    public FeieResult(Integer ret, String msg, T data) {
        this.ret = ret;
        this.msg = msg;
        this.data = data;
    }
    public static FeieResult build(Integer ret, String msg, Object data) {
        return new FeieResult(ret, msg, data);
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
