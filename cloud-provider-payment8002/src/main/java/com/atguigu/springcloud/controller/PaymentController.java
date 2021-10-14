package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
public class PaymentController {

//    @Autowired
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        log.info("进入PaymentController---/payment/create方法，参数payment：" + payment);
        int result = paymentService.create(payment);
        log.info("*****插入结果是：" + result);
        if (result > 0){
            return new CommonResult(200 , "插入数据库成功,serverPort:" + serverPort , result);
        }else{
            return new CommonResult(444 , "插入数据库失败" , null);
        }
    }

    @ResponseBody
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        log.info("进入PaymentController---/payment/get方法，参数id:" +id);
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null){
            log.info("*****查询结果是：" + payment.toString());
        }

        if (payment != null){
            return new CommonResult(200 , "查询成功,serverPort:" + serverPort , payment);
        }else{
            return new CommonResult(444 , "没有对应记录，查询ID：" + id , null);
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    }

}
