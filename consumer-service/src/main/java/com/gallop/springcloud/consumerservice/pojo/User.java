package com.gallop.springcloud.consumerservice.pojo;

import lombok.Data;

/**
 * author gallop
 * date 2020-05-25 12:19
 * Description:
 * Modified By:
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String mchId;
    private String mobile;
    private String userToken;
}
