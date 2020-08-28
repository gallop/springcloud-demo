package com.gallop.springloud.mq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author gallop
 * date 2020-06-03 18:14
 * Description:
 * Modified By:
 */
@Configuration
public class RabbitmqConfig {
    @Value("${spring.rabbitmq.listener.mysimple.acknowledge-mode}")
    private String acknowledge_mode;

    @Value("${spring.rabbitmq.listener.mysimple.concurrency}")
    private Integer concurrency;

    @Value("${spring.rabbitmq.listener.mysimple.prefetch}")
    private Integer prefetch;

    @Value("${spring.rabbitmq.listener.mysimple.max-concurrency}")
    private Integer maxConcurrency;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConcurrentConsumers(concurrency);
        factory.setMaxConcurrentConsumers(maxConcurrency);
        factory.setPrefetchCount(prefetch);
        return factory;
    }
}
