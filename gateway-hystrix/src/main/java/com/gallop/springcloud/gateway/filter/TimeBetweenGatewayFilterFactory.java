package com.gallop.springcloud.gateway.filter;

import com.gallop.common.constatns.ResponseCode;
import com.gallop.common.utils.JSONResult;
import com.gallop.springcloud.gateway.config.TimeBetweenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalTime;

/**
 * author gallop
 * date 2020-05-25 12:46
 * Description: 可对客户端header 中的 Authorization 信息进行认证
 * Modified By:
 */
@Component
public class TimeBetweenGatewayFilterFactory extends AbstractGatewayFilterFactory {
    @Autowired
    private TimeBetweenConfig timeBetweenConfig;

    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpRequest.Builder mutate = request.mutate();
            HttpMethod method = request.getMethod();
            ServerHttpResponse response = exchange.getResponse();

            if(HttpMethod.POST.equals(method) && !isIgnoreUri(path) && !isOperate(timeBetweenConfig)){
                DataBuffer bodyDataBuffer = JSONResult.responseErrorInfo(response , ResponseCode.OUT_OF_OPERATE_HOURS ,"该店还未开门！");
                return response.writeWith(Mono.just(bodyDataBuffer));
            }
            ServerHttpRequest build = mutate.build();
            return chain.filter(exchange.mutate().request(build).build());
        };
    }

    private boolean isOperate(TimeBetweenConfig timeBetweenConfig){
        if(!timeBetweenConfig.isOperate()){
            return false;
        }

        LocalTime start = timeBetweenConfig.getStart();
        LocalTime end = timeBetweenConfig.getEnd();

        // 判断当前时间是否为允许访问的时间段内
        LocalTime now = LocalTime.now();
        return now.isAfter(start) && now.isBefore(end);
    }

    /**
     * date 2020/5/26 13:01
     * Description: 判断是不是无需filter的uri
     * Param:
     * return:
     **/
    private boolean isIgnoreUri(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.contains(s)) {
                return true;
            }
        }
        return flag;
    }
}
