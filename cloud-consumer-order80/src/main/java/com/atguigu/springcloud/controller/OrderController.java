package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author puruichuan
 * @create 2021-03-09 11:50
 */

@RestController
@Slf4j
public class OrderController {

//    public static final String PAYMENT_URL="http://localhost:8001"; //单机版payment
    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";    //集群版payment

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        log.info("进入订单---OrderController---/consumer/payment/create---参数："+payment.toString());
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create" , payment , CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id")Long id){
        log.info("进入订单---OrderController---/consumer/payment/get/{id}---参数：id="+id);
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id , CommonResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id")Long id){
        log.info("进入订单---OrderController---/consumer/payment/get/{id}---参数：id="+id);
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id , CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult<>(444 , "操作失败");
        }
    }

    @GetMapping("/consumer/payment/create2")
    public CommonResult<Payment>  create2(Payment payment){
        log.info("进入订单---OrderController---/consumer/payment/create---参数："+payment.toString());
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL + "/consumer/payment/create", payment, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult<>(444 , "操作失败！");
        }
    }

    @GetMapping("/consume/payment/lb")
    public String getPaymentLB(){
        //根据服务ID查询当前服务列表
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0){
            return null;
        }
        //获取被访问服务的信息
        ServiceInstance instance = loadBalancer.instance(instances);
        //得到被访问服务的URI地址
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb" , String.class);
    }

}
