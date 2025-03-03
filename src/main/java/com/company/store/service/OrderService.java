package com.company.store.service;

import com.company.store.dto.OrderData;
import com.company.store.dto.OrderDto;
import com.company.store.model.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderDto getOrderById(Long id);

    List<Long> getOrdersIdsByStatus(OrderStatus status);

    List<OrderDto> getAllOrders();

    OrderDto createOrder(OrderData orderRequest, Long userId);

    void payOrder(Long id);

}
