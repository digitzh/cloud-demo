package com.digitzh.product;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@SpringBootTest
public class DiscoveryTest {
    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    NacosServiceDiscovery nacosServiceDiscovery;

    @Test
    void NacosServiceDiscoveryTest() throws NacosException {
        for (String service : nacosServiceDiscovery.getServices()){
            System.out.println(service);
            // 获取IP+port
            List<ServiceInstance> instances = nacosServiceDiscovery.getInstances(service);
            for (ServiceInstance instance : instances){
                System.out.println("IP: " + instance.getHost() + ", " + "port: " + instance.getPort());
            }
        }
    }

    @Test
    void discoveryClientTest(){
        for (String service : discoveryClient.getServices()){
            System.out.println(service);
            // 获取IP+port
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances){
                System.out.println("IP: " + instance.getHost() + ", " + "port: " + instance.getPort());
            }
        }
    }
}
