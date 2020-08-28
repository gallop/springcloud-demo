package com.gallop.springcloud.consumerservice.controller;

import com.gallop.common.utils.FeieResult;
import com.gallop.springcloud.consumerservice.vo.FeiePrintParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * author gallop
 * date 2020-06-03 22:45
 * Description:
 * Modified By:
 */
@RestController
@Slf4j
public class FeieController {

    @RequestMapping(value = "/feieapi/print",method = RequestMethod.POST)
    public Object pirnt(@RequestBody Map<String,Object> map){
        log.error("FeiePrintParams:"+map.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return FeieResult.build(0,"打印成功","sn:123456");
    }
}
