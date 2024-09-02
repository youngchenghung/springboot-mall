package com.leo.springboot_mall.dao;

import java.util.List;

import com.leo.springboot_mall.dto.OrderQueryParams;
import com.leo.springboot_mall.model.Order;
import com.leo.springboot_mall.model.OrderItem;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);
}
