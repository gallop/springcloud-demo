package com.gallop.springloud.mq.dto;

import lombok.Data;

/**
 * author gallop
 * date 2020-06-21 10:55
 * Description:
 * Modified By:
 */
@Data
public class TakeoutPrinter {
    private Integer id;
    private Integer mchId;
    private Integer type;
    private String sn;
    private String key;
    private String remark;
    private String carnum;
}
