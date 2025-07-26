package com.digitzh.order.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.digitzh.order.bean.Order;
import com.digitzh.order.feign.ProductFeignClient;
import com.digitzh.order.service.OrderService;
import com.digitzh.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    ProductFeignClient productFeignClient;

    @SentinelResource(value = "createOrder", fallback = "createOrderFallback")
    @Override
    public Order createOrder(Long productId, Long userId) {
        // 假设只有1种商品
//        Product product = getProductFromRemoteWithLoadBalancerAnnotation(productId);
        Product product = productFeignClient.getProductById(productId);
        Order order = new Order();
        order.setId(productId);
        // 总金额（==单价*数量，product中包含了商品数量）
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("Zhangsan");
        order.setAddress("Shanghai");
        // 商品列表
        order.setProductList(List.of(product));

        return order;
    }

    public Order createOrderFallback(Long productId, Long userId, Throwable e){
        Order order = new Order();
        order.setId(0L);
        order.setTotalAmount(new BigDecimal(0));
        order.setUserId(userId);
        order.setNickName("Error");
        order.setAddress("Error"+e.getClass());
        return order;
    }

    // I 无负载均衡的远程调用
    private Product getProductFromRemote(Long productId){
        // 1. 获取到商品服务所在的所有机器IP+Product
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");

        // 假设获取第1个
        ServiceInstance instance = instances.get(0);
        // 远程URL
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/product/" + productId;
        log.info("远程请求: {}", url);

        // 2. 给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    // II 有负载均衡的远程调用（loadBalancerClient实现）
    private Product getProductFromRemoteWithLoadBalancer(Long productId){
        // 1. 获取到商品服务所在的一个机器IP+Product
        ServiceInstance choose = loadBalancerClient.choose("service-product");

        // 远程URL
        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/product/" + productId;
        log.info("远程请求: {}", url);

        // 2. 给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    // III 有负载均衡的远程调用（注解实现）
    private Product getProductFromRemoteWithLoadBalancerAnnotation(Long productId){
        String url = "http://service-product/product/"+productId;

        // 2. 给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }
}
