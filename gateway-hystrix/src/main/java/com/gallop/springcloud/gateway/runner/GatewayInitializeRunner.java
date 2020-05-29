package com.gallop.springcloud.gateway.runner;

import com.gallop.common.serialization.OperateTimeBetween;
import com.gallop.springcloud.gateway.config.TimeBetweenConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;

/**
 * author gallop
 * date 2020-05-26 9:55
 * Description:系统启动完后做一些初始化的工作
 * Modified By:
 */
@Component
@Order(-10)
@Slf4j
public class GatewayInitializeRunner implements CommandLineRunner {
    @Autowired
    private TimeBetweenConfig timeBetweenConfig;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("===========================MyCommandLineRunner============================");
        // -----------------模拟王redis初始化一些数据-----------------------
        OperateTimeBetween operateTimeBetween = new OperateTimeBetween();
        LocalTime start = LocalTime.parse("14:00:00");
        LocalTime end = LocalTime.parse("22:00:00");
        operateTimeBetween.setOperate(true);
        operateTimeBetween.setStart(start);
        operateTimeBetween.setEnd(end);

        redisTemplate.opsForValue().set("mch001",operateTimeBetween);
        stringRedisTemplate.opsForSet().add("clientToken","test1","test2");
        OperateTimeBetween operateTimeBetween_new = (OperateTimeBetween)redisTemplate.opsForValue().get("mch001");

        Set<String> clientTokemSet = stringRedisTemplate.opsForSet().members("clientToken");
        log.info("operateTimeBetween_new="+operateTimeBetween_new.toString());
        if(operateTimeBetween_new != null){
            BeanUtils.copyProperties(operateTimeBetween_new ,timeBetweenConfig);
        }
        log.info("timeBetweenConfig:{}",timeBetweenConfig.toString());
        log.info("clientToken:"+clientTokemSet.size() + ",values:"+clientTokemSet.toString());
        clientTokemSet.forEach((tokenTmp)->{
            log.info(tokenTmp);
        });
        System.out.println("===========================================================================");

    }
}
