package com.gallop.springcloud.gateway.feign;

import com.gallop.common.utils.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author gallop
 * date 2020-05-26 22:01
 * Description:
 * Modified By:
 */
@FeignClient(value = "${auth.serviceId}",configuration = {})
public interface ServiceAuthFeign {
    @PostMapping(value = "/pubkey/jwtPubKey",consumes = MediaType.APPLICATION_JSON_VALUE)
    public JSONResult<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId);
}
