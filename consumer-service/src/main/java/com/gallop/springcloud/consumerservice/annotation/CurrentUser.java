package com.gallop.springcloud.consumerservice.annotation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * author gallop
 * date 2020-05-27 12:06
 * Description:
 * Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUser implements Serializable {
    private static final long serialVersionUID = 5973508444164852607L;
    private Integer userId;
    private Integer mchId;

}
