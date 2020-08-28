package com.gallop.springcloud.consumerservice.vo;

import lombok.Data;

/**
 * author gallop
 * date 2020-06-03 22:52
 * Description:
 * Modified By:
 */
@Data
public class FeiePrintParams {
    private String user;
    private String stime;
    private String sig;
    private String apiname;
    private String sn;
    private String content;
    private String times;

}
