package com.gallop.springcloud.gateway.controller;

import com.gallop.common.utils.JSONResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author gallop
 * date 2020-05-27 17:19
 * Description:
 * Modified By:
 */
@RestController
public class DefaultHystrixController {
    @RequestMapping("/defaultfallback")
    public Object defaultfallback(){
        System.out.println("降级操作...");
        return JSONResult.errorMsg(404,"服务降级啦！");
    }
}
