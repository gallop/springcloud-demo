package com.gallop.springcloud.gateway.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

/**
 * author gallop
 * date 2020-05-25 10:55
 * Description:
 * Modified By:
 */
@Data
@Configuration
public class TimeBetweenConfig {
    private boolean isOperate; //运营状态开关
    /**
     * 开始时间
     */
    private LocalTime start;

    /**
     * 结束时间
     */
    private LocalTime end;
}
