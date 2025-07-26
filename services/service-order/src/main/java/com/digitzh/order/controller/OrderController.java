package com.digitzh.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.digitzh.order.bean.Order;
import com.digitzh.order.properties.OrderProperties;
import com.digitzh.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 使用Gateway的"路径重写"，可省略该注解
//@RequestMapping("/api/order")
@Slf4j
//@RefreshScope
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

//    @Value("${order.timeout}")
//    String orderTimeout;
//    @Value("${order.auto-confirm}")
//    String orderAutoConfirm;

    @Autowired
    OrderProperties orderProperties;

    @GetMapping("/config")
    public String config(){
        return "order.timeout="+orderProperties.getTimeout()
                +": order.auto-confirm="+orderProperties.getAutoConfirm()
                +": order.db-url="+orderProperties.getDbUrl();
    }

    @GetMapping("/create")
    public Order createOrder(@RequestParam("productId") Long productId,
                             @RequestParam("userId") Long userId){
        Order order = orderService.createOrder(productId, userId);
        return order;
    }

    // 测试Sentinel流控 - 链路模式
    @GetMapping("/seckill")
    @SentinelResource(value = "seckill-order", blockHandler = "seckillFallback")
    public Order seckill(@RequestParam(value = "userId", required = false) Long userId,
                         @RequestParam(value = "productId", defaultValue = "1") Long productId){
        Order order = orderService.createOrder(productId, userId);
        order.setId(Long.MAX_VALUE);  // 与普通创建订单区分
        return order;
    }

    public Order seckillFallback(Long userId, Long productId, Throwable e){
        log.info("seckillFallback...");
        Order order = new Order();
        order.setId(Long.MAX_VALUE);
        order.setUserId(userId);
        order.setAddress("Error: "+e.getClass());
        return order;
    }

    // 测试Sentinel流控 - 关联模式
    @GetMapping("/writeDb")
    public String writeDb(){
        return "writeDb success!";
    }

    @GetMapping("/readDb")
    public String readDb(){
        log.info("readDb...");
        return "readDb success!";
    }
}
