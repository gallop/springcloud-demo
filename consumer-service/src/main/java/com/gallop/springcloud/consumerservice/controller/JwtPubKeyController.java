package com.gallop.springcloud.consumerservice.controller;

import com.gallop.common.utils.JSONResult;
import com.gallop.springcloud.consumerservice.config.JwtKeyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * author gallop
 * date 2020-05-26 21:52
 * Description:
 * Modified By:
 */
@RestController
@RequestMapping("/pubkey")
public class JwtPubKeyController {
    @Autowired
    private JwtKeyConfiguration jwtKeyConfiguration;
    @RequestMapping(value = "/jwtPubKey",method = RequestMethod.POST)
    public Object getUserPublicKey(@RequestParam("clientId") String clientId) throws Exception {
        //authClientService.validate(clientId, secret);
        return JSONResult.ok(jwtKeyConfiguration.getUserPubKey());
    }
}
