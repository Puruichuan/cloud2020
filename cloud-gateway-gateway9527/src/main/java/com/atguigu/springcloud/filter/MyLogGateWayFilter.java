package com.atguigu.springcloud.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @Author : Mr、Puruichuan
 * @Date : 2021/10/14-10:26 上午
 * @Version : V1.0
 * @TUDO : **
 */
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter , Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("-------------- come in ------------" + new Date());
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if(StringUtils.isBlank(uname)){
            log.info("------------用户名不能为空，该用户为非法用户----------");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
