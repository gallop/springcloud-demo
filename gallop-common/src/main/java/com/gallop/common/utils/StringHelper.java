package com.gallop.common.utils;

/**
 * author gallop
 * date 2020-05-26 20:30
 * Description:
 * Modified By:
 */
public class StringHelper {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }
}
