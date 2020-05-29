package com.gallop.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages= {"com.gallop"})
@EnableFeignClients({"com.gallop.springcloud.gateway.feign"})
public class GatewayHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayHystrixApplication.class, args);
    }

}
