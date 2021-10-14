package com.atguigu.springcloud.service;


import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 这个方法肯定是OK的，没问题，可以直接访问
     * @param id
     * @return
     */
    public String paymentInfo_ok(Integer id){
        return "线程池: " + Thread.currentThread().getName() + " paymentInfo_ok,id: " + id +"\t" + "哈哈哈哈";
    }

    /**
     * 这个方法肯定是OK的，没问题，可以直接访问
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler" , commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds" , value = "5000")
    })
    public String paymentInfo_timeout(Integer id){
//        int sum =  10 / 0 ;
        int timeoutNumber = 13;
        try {
            TimeUnit.SECONDS.sleep(timeoutNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池: " + Thread.currentThread().getName() + " paymentInfo_timeout,id: " + id +"\t" + "哈哈哈哈... 耗时（单位：秒）：" + timeoutNumber;
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池: " + Thread.currentThread().getName() + " 系统繁忙，请稍后重试！,id: " + id +"\t" + "哈哈哈哈...fallback ";
    }

    //===========服务熔断==========
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback" , commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled" , value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold" , value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds" , value="10000"),//时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage" , value="60")//失败率达到多少后跳闸
    })
    public String paymentCircuitBreak(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("****** id 不能为负数 ******");
        }
        String serialNumer = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+ "/t" +"调用成功，流水号：" + serialNumer;

    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请重新调整后再试，id=" +id;
    }


}
