package com.gallop.springcloud.consumerservice.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gallop.common.serialization.Order;
import com.gallop.springcloud.consumerservice.enums.QueueEnum;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * author gallop
 * date 2020-06-03 11:11
 * Description:
 * Modified By:
 */
@Component
public class RabbitSender {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //回调函数: confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        System.err.println("correlationData: " + correlationData);
        System.err.println("ack: " + ack);
        if(!ack){
            System.err.println("异常处理....");
        }
    };

    //回调函数: return返回
    final RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey)
            -> {System.err.println("return exchange: " + exchange + ", routingKey: "
            + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);};

    //发送消息方法调用: 构建Message消息
    public void send(Object message, Map<String, Object> properties) throws Exception {
        MessageHeaders mhs = new MessageHeaders(properties);
        Order order = new Order();
        order.setId(10001);
        order.setName("gallop");
        order.setContent("haha! this the first mq message!!");
        Message msg = MessageBuilder.createMessage(order, mhs);

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

        rabbitTemplate.setExchange(QueueEnum.FEIER.getExchange());
        //rabbitTemplate.setRoutingKey(QueueEnum.FEIER.getRouteKey()); //QueueEnum.FEIER.getRouteKey()
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        Long timeStart = System.currentTimeMillis();
        rabbitTemplate.convertAndSend(QueueEnum.FEIER.getRouteKey(),msg, correlationData);
        System.err.println("====队列发送时间："+ (System.currentTimeMillis()-timeStart));
        //rabbitTemplate.convertAndSend("exchange-1", "springboot.abc", msg, correlationData);

    }
}
