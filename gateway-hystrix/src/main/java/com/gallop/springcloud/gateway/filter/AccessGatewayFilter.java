package com.gallop.springcloud.gateway.filter;

import com.gallop.common.auth.jwt.IJWTInfo;
import com.gallop.common.exception.auth.UserTokenException;
import com.gallop.common.utils.JSONResult;
import com.gallop.common.utils.RedisOperator;
import com.gallop.springcloud.gateway.auth.jwt.UserAuthUtil;
import com.gallop.springcloud.gateway.config.UserJwtKeyConfig;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * author gallop
 * date 2020-05-26 10:32
 * Description:
 * Modified By:
 */
@Component
@Slf4j
@Configuration
public class AccessGatewayFilter implements GlobalFilter, Ordered {
    //private static final String MCH_CLIENT_TOKEN = "x-mch-client-token";
    //private static final String MCH_USER_TOKEN = "x-mch-user-token";
    private static final String CLIENT_TOKEN_PRIFIX = "gallop_";
    private static final String CLIENT_TOKEN_REDIS_KEY = "clientToken";

    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private UserJwtKeyConfig userJwtKeyConfig;
    @Autowired
    private UserAuthUtil userAuthUtil;

    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        long start = System.currentTimeMillis();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        ServerHttpResponse response = exchange.getResponse();
        //验证商户客户端token
        try {
            //获取header中的Authorization
            String clientToken = request.getHeaders().getFirst(userJwtKeyConfig.getClientHeader());
            System.out.println("header Authorization : " + clientToken);
            if (clientToken == null || !clientToken.startsWith(CLIENT_TOKEN_PRIFIX)) {
                throw new RuntimeException("非法的商户客户端请求！");
            }
            //截取Authorization Bearer
            String token = clientToken.substring(7);
            //可把token存到redis中，此时直接在redis中判断是否有此key，有则校验通过，否则校验失败
            if(StringUtils.isEmpty(token) || !checkTokenValid(token)){
                System.out.println("验证不通过");
                //有token，把token设置到header中，传递给后端服务
                throw new RuntimeException("商户客户端token无效！");
            }else{
                System.out.println("token有效");
                //mutate.header("userDetails",token).build();
            }
        }catch (Exception e){
            //没有token
            DataBuffer bodyDataBuffer = JSONResult.responseErrorInfo(response , HttpStatus.UNAUTHORIZED.value() ,e.getMessage());
            return response.writeWith(Mono.just(bodyDataBuffer));
        }
        //验证用户合法性
        //除配置的ignore的uri 外，其余的都要检查并解析jwt
        if(!isIgnoreUri(path)){
            //检查token是否有效，并解析jwt token 并把值设置到header中，传递给后端服务
            IJWTInfo user = null;
            try {
                user = getJWTUser(request, mutate);
                log.info("user-token:{}",user.toString());
                mutate.header("mch_id",user.getMchId());
                mutate.header("user_id",user.getUserId());
            } catch (UserTokenException e) {
                log.error("用户Token异常", e);
                return getVoidMono(exchange,e.getStatus(), e.getMessage());
            }

        }

        ServerHttpRequest build = mutate.build();
        return chain.filter(exchange.mutate().request(build).build())
                .then(Mono.fromRunnable(() ->
                    log.info("[ {} ] 接口的访问耗时：{} /ms", path, System.currentTimeMillis() - start))
        );
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private boolean checkTokenValid(String token){
        return redisOperator.isMemberSet(CLIENT_TOKEN_REDIS_KEY,token);
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

     /**
      * date 2020/5/26 22:34
      * Description: 返回token session中的用户信息
      * Param: 
      * return: 
      **/
    private IJWTInfo getJWTUser(ServerHttpRequest request, ServerHttpRequest.Builder ctx) throws UserTokenException {
        List<String> strings = request.getHeaders().get(userJwtKeyConfig.getTokenHeader());
        String authToken = null;
        if (strings != null) {
            authToken = strings.get(0);
        }
        if (StringUtils.isBlank(authToken)) {
            strings = request.getQueryParams().get("token");
            if (strings != null) {
                authToken = strings.get(0);
            }
        }
        if(authToken == null){
            throw new UserTokenException("User token is null or empty!");
        }
        ctx.header(userJwtKeyConfig.getTokenHeader(), authToken);
        //BaseContextHandler.setToken(authToken);
        return userAuthUtil.getInfoFromToken(authToken);
    }

    /**
     * 网关抛异常
     *
     * @param body
     */
    @NotNull
    private Mono<Void> getVoidMono(ServerWebExchange serverWebExchange,Integer status, String msg) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        DataBuffer bodyDataBuffer = JSONResult.responseErrorInfo(serverWebExchange.getResponse() , status ,msg);
        return serverWebExchange.getResponse().writeWith(Flux.just(bodyDataBuffer));
    }

}
