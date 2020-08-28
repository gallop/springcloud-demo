package com.gallop.springcloud.consumerservice.controller;

import com.gallop.common.utils.JSONResult;
import com.gallop.springcloud.consumerservice.components.AmqpSender;
import com.gallop.springcloud.consumerservice.components.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * author gallop
 * date 2020-06-03 11:24
 * Description:
 * Modified By:
 */
@RestController
public class RabbitController {
    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private AmqpSender amqpSender;

    @RequestMapping(value = "/rabbitmq/send",method = RequestMethod.GET)
    public Object rabbitmqSendMsg(){
        System.err.println("into /rabbitmq/send ......");
        String msg = "haha! ampq:the first rabbit mq message!!";
        Map<String,Object> properties = new HashMap<>();
        Long timeStart = System.currentTimeMillis();
        try {
            amqpSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("执行时间："+ (System.currentTimeMillis()-timeStart));

        return JSONResult.ok();
    }

    @RequestMapping(value = "/rabbitmq/test",method = RequestMethod.GET)
    public Object test(){
        System.err.println("into /rabbitmq/test ......");

        return JSONResult.ok();
    }
}
