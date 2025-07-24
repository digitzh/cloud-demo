package com.digitzh.order;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;


@SpringBootTest
public class LoadBalancerTest {
    @Autowired
    LoadBalancerClient loadBalancerClient;
    DiscoveryClient discoveryClient;

    // TODO: 报错，原因不明：
    // java.lang.NullPointerException: Cannot invoke "org.springframework.cloud.client.loadbalancer.
    // LoadBalancerClient.choose(String)" because "this.loadBalancerClient" is null
    @Test
    public void test(){
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = " + choose.getHost() + ":" + choose.getPort());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = " + choose.getHost() + ":" + choose.getPort());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = " + choose.getHost() + ":" + choose.getPort());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = " + choose.getHost() + ":" + choose.getPort());
    }
}
