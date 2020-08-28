package com.gallop.springcloud.consumerservice.config;

import com.gallop.springcloud.consumerservice.enums.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * author gallop
 * date 2020-06-03 10:55
 * Description:
 * Modified By:
 */
@Component
public class RabbitMqConfig {

    @Bean
    TopicExchange testTopic(){
        return ExchangeBuilder.topicExchange(QueueEnum.TEST.getExchange())
                .durable(true)
                .build();
    }

     /**
      * date 2020/6/3 11:00
      * Description: 飞蛾订单消息队列所绑定的交换机
      * Param:
      * return:
      **/
    @Bean
    DirectExchange feierDirect(){
        return ExchangeBuilder.directExchange(QueueEnum.FEIER.getExchange())
                .durable(true)
                .build();
    }

    @Bean
    public Queue testQueue(){
        return new Queue(QueueEnum.TEST.getQueueName());
    }

    @Bean
    public Queue feierQueue(){
        return QueueBuilder
                .durable(QueueEnum.FEIER.getQueueName())
                .build();
    }

    @Bean
    Binding testBinding(TopicExchange testTopic,Queue testQueue){
        return BindingBuilder
                .bind(testQueue)
                .to(testTopic)
                .with(QueueEnum.TEST.getRouteKey());
    }

    @Bean
    Binding feierBinding(DirectExchange feierDirect,Queue feierQueue){
        return BindingBuilder
                .bind(feierQueue)
                .to(feierDirect)
                .with(QueueEnum.FEIER.getRouteKey());
    }

}
