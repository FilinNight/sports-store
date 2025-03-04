package com.company.store.service;

import com.company.store.dto.OrderData;
import com.company.store.model.Order;
import com.company.store.model.OrderStatus;

import java.util.List;

public interface OrderService {

    Order getOrderById(Long id);

    List<Long> getOrdersIdsByStatus(OrderStatus status);

    List<Order> getAllOrders();

    Order createOrder(OrderData orderData, Long userId);

    Order confirmOrder(Long orderId, Long userId);

    Order payOrder(Long orderId, Long userId);

    Order cancelOrder(Long orderId, Long userId);

}
