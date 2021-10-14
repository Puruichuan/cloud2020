package com.atguigu.springcloud.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Component
@Slf4j
public class MyLB implements LoadBalancer {

    //记录当前是第几次访问的次数
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 获取当前访问是第几次
     * @return
     */
    private final int getAndIncrement(){
        int current;
        int next;
        do {
            //获取上一次访问的时候是第几次访问
            current = this.atomicInteger.get();
            //2147483647这个值是最大整型值
            next = current >= 2147483647 ? 0 : current + 1;
            //CAS操作，比较并赋值
        }while (!this.atomicInteger.compareAndSet(current , next));
            log.info("当前访问次数为:next="+ next +"次！");
            return next;
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstance) {
        //计算下一次被访问服务信息的下标值
        int index = getAndIncrement() % serviceInstance.size();
        //通过下标返回当前被访问的服务信息
        return serviceInstance.get(index);
    }
}
