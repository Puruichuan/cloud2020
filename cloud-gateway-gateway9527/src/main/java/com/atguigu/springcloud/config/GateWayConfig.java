package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : Mr、Puruichuan
 * @Date : 2021/10/13-3:02 下午
 * @Version : V1.0
 * @TUDO : **
 */
@Configuration
public class GateWayConfig {
    /**
     * 配置了一个ID为route-name 的路由规则
     * 当前访问的地址为：http://localhost:9527/guonei 时会自动转发到地址：http://news.baidu.com/guonei
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator cunstomRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_routh_atguigu1" , r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }

    @Bean
   public RouteLocator guojiNewsRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
       RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
       routes.route("path_routh_prc" , r -> r.path("/guoji").uri("http://news.baidu.com/guoji")).build();
       return routes.build();
   }
}
