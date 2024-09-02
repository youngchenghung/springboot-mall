package com.leo.springboot_mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.leo.springboot_mall.dao.OrderDao;
import com.leo.springboot_mall.dao.ProductDao;
import com.leo.springboot_mall.dao.UserDao;
import com.leo.springboot_mall.dto.BuyItem;
import com.leo.springboot_mall.dto.CreateOrderRequest;
import com.leo.springboot_mall.model.Order;
import com.leo.springboot_mall.model.OrderItem;
import com.leo.springboot_mall.model.Product;
import com.leo.springboot_mall.model.User;
import com.leo.springboot_mall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        // 檢查 user 是否存在
        User user = userDao.getUserById(userId);
        
        if (user == null) {
            log.warn("UserId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查商品是否存在
            if (product == null) {
                log.warn("ProductId {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            // 檢查商品庫存是否足夠
            else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("ProductId {} 庫存不足", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 當訂單建立,更新商品庫存就需要扣除
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            // 計算總金額
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            // 購物車商品轉換為訂單商品
            // 轉換 buyItem 為 orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
        
    }
}
