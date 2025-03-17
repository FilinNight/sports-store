package com.company.store.service;

import com.company.store.model.Order;

public interface NotificationService {

    void sendNotificationByCreateOrder(Order order);
}
