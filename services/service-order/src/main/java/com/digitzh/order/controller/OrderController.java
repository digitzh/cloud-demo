package com.digitzh.order.controller;

import com.digitzh.order.bean.Order;
import com.digitzh.order.properties.OrderProperties;
import com.digitzh.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
