package com.example.firstservice.service;

import com.example.firstservice.dto.OrderDto;
import com.example.firstservice.entity.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
