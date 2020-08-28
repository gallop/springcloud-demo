package com.gallop.springloud.mq.enums;

import lombok.Getter;

/**
 * author gallop
 * date 2020-06-03 10:45
 * Description:
 * Modified By:
 */
@Getter
public enum QueueEnum {
    TEST("gallop.test.topic","queue.test","gallop.test.sendmsg"),
    FEIER("gallop.feier.topic","queue.feier","gallop.feier.sendmsg");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String queueName;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String queueName, String routeKey) {
        this.exchange = exchange;
        this.queueName = queueName;
        this.routeKey = routeKey;
    }
}
