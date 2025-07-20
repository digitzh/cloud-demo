package com.digitzh.order.service;

import com.digitzh.order.bean.Order;

public interface OrderService {
    Order createOrder(Long productId, Long userId);
}
