package com.gallop.springcloud.consumerservice.controller;

import com.gallop.common.utils.JSONResult;
import com.gallop.springcloud.consumerservice.annotation.CurrentUser;
import com.gallop.springcloud.consumerservice.annotation.LoginUser;
import com.gallop.springcloud.consumerservice.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author gallop
 * date 2020-05-24 12:12
 * Description:
 * Modified By:
 */
@RestController
@Slf4j
public class IndexController {

    @GetMapping("/hello")
    public Object hello(@LoginUser CurrentUser user, String name){
        log.info("current user: {}",user.toString());

        return JSONResult.ok(user);
    }

    @GetMapping("/timeout")
    public String timeout(){
        try{
            //睡5秒，网关Hystrix3秒超时
            Thread.sleep(4000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "timeout";
    }
    @PostMapping("/addCart")
    public Object addCart(){
        log.info("into the /addCart ......");

        return JSONResult.ok();
    }
}
