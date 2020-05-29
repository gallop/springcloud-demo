package com.gallop.springcloud.gateway.predicate;

import com.gallop.springcloud.gateway.config.TimeBetweenConfig;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * author gallop
 * date 2020-05-25 10:56
 * Description:路由断言工厂必须以RoutePredicateFactory结尾，
 *   这是Spring Cloud Gateway的约定
 * Modified By:
 */
@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenConfig.class);
    }

    /**
     * 实现谓词判断的方法
     */
    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
        return exchange -> {
            LocalTime start = config.getStart();
            LocalTime end = config.getEnd();

            // 判断当前时间是否为允许访问的时间段内
            LocalTime now = LocalTime.now();
            return now.isAfter(start) && now.isBefore(end);
        };
    }

    /**
     * 控制配置类（TimeBetweenConfig）属性和配置文件中配置项（TimeBetween）的映射关系
     */
    @Override
    public List<String> shortcutFieldOrder() {
        /*
         * 例如我们的配置项是：TimeBetween=上午9:00, 下午5:00
         * 那么按照顺序，start对应的是上午9:00；end对应的是下午5:00
         **/
        return Arrays.asList("start", "end");
    }
}
