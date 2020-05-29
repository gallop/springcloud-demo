package com.gallop.springcloud.gateway.runner;

import com.gallop.common.utils.JSONResult;
import com.gallop.springcloud.gateway.config.UserJwtKeyConfig;
import com.gallop.springcloud.gateway.feign.ServiceAuthFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * author gallop
 * date 2020-05-26 22:13
 * Description:
 * Modified By:
 */
@Component
@Order(-5)
@Slf4j
public class UserJwtPubKeyRunner implements CommandLineRunner {
    @Autowired
    private UserJwtKeyConfig userJwtKeyConfig;
    @Autowired
    private ServiceAuthFeign serviceAuthFeign;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化加载用户pubKey");
        try {
            refreshUserPubKey();
        }catch(Exception e){
            log.error("初始化加载用户pubKey失败,5分钟后自动重试!",e);
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void refreshUserPubKey(){
        JSONResult<byte[]> resp = serviceAuthFeign.getUserPublicKey("mch_001");
        if (resp.getStatus() == HttpStatus.OK.value()) {
            log.info("get getUserPublicKey success!");
            this.userJwtKeyConfig.setPubKeyByte(resp.getData());
        }
    }
}
