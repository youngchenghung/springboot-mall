package com.leo.springboot_mall.service;

import java.util.List;

import com.leo.springboot_mall.dto.CreateOrderRequest;
import com.leo.springboot_mall.dto.OrderQueryParams;
import com.leo.springboot_mall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);
}
