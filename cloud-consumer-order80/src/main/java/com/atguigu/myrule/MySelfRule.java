package com.atguigu.myrule;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        //定义为随机算法，默认为轮训算法（RoundRobinRule）
        return new RandomRule();
    }
}
