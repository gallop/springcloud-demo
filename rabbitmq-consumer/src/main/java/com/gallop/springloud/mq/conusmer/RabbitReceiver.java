package com.gallop.springloud.mq.conusmer;

import com.gallop.common.serialization.Order;
import com.gallop.common.utils.FeieResult;
import com.gallop.springloud.mq.components.FeieApi;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * author gallop
 * date 2020-06-03 16:38
 * Description:
 * Modified By:
 */
@Component
@Slf4j
@org.springframework.core.annotation.Order(-10)
public class RabbitReceiver {

    @Autowired
    private FeieApi feieApi;

   /* @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue.feier",
                    durable="true"),
            exchange = @Exchange(value = "gallop.feier.topic",
                    durable="true",
                    type= "direct",
                    ignoreDeclarationExceptions = "true"),
            key = "gallop.feier.sendmsg"
    )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {

        System.err.println("--------------------------------------");
        //String msg = JsonUtils.objectToJson(message.getPayload());
        Jackson2JsonMessageConverter jackson2JsonMessageConverter =new Jackson2JsonMessageConverter();
        Order order = JsonUtils.jsonToPojo(new String(message.getBody()),Order.class);
        //org.springframework.messaging.Message msg = (org.springframework.messaging.Message)message;
        //msg.getPayload();
        //JsonUtils.

        //byte[] body = message.getBody();

        //String msg = new java.lang.String(body);
        //Ojackson2JsonMessageConverter.fromMessage(message);
        System.err.println("消费端Payload: " + order.toString());
        Long deliveryTag = (Long)message.getMessageProperties().getDeliveryTag();
        Thread.sleep(2000);
        //手工ACK
        channel.basicAck(deliveryTag, false);
    }*/

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue.feier",
                    durable="true"),
            exchange = @Exchange(value = "gallop.feier.topic",
                    durable="true",
                    type= "direct",
                    ignoreDeclarationExceptions = "true"),
            key = "gallop.feier.sendmsg"
    )
    )
    @RabbitHandler
    public void onMessage(Message message,Order order, Channel channel) throws Exception {
        //containerFactory = "rabbitListenerContainerFactory"
        System.err.println("---------------handleMessage-----------------------");
        //Jackson2JsonMessageConverter jackson2JsonMessageConverter =new Jackson2JsonMessageConverter();
        //Order order = (Order)jackson2JsonMessageConverter.fromMessage(message);

        log.error("消费端Payload: " + order.toString());
        Long deliveryTag = (Long)message.getMessageProperties().getDeliveryTag();
        System.err.println("deliveryTag:"+deliveryTag);
        FeieResult result = feieApi.printMsg("feie:123456","外卖打印测试内容",1);
        System.err.println("result:"+result.getMsg());
        //手工ACK
        channel.basicAck(deliveryTag, false);
    }
}
