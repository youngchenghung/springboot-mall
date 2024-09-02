package com.leo.springboot_mall.service;

import com.leo.springboot_mall.dto.CreateOrderRequest;
import com.leo.springboot_mall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
