package com.company.store.service;

import com.company.store.model.OrderStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final OrderService orderService;

    public TaskService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "${task.com.company.store.payment-order.cron}")
    public void payOrders() {
        List<Long> ordersIds = orderService.getOrdersIdsByStatus(OrderStatus.CREATE);
        ordersIds.forEach(orderService::payOrder);
    }
}
