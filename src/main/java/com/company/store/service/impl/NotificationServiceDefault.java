package com.company.store.service.impl;

import com.company.store.dto.NotificationDto;
import com.company.store.dto.NotificationType;
import com.company.store.model.Order;
import com.company.store.service.NotificationService;
import com.company.store.service.producer.NotificationProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceDefault implements NotificationService {

    private final NotificationProducer notificationProducer;

    @Override
    public void sendNotificationByCreateOrder(Order order) {
        NotificationDto notificationDto = NotificationDto.builder()
                .userId(order.getCustomer().getUser().getId())
                .email(order.getCustomer().getEmail())
                .phone(order.getCustomer().getPhone())
                .type(NotificationType.PHONE)
                .message("Order № " + order.getId() + " was created for the amount of " + order.getTotalPrice())
                .build();
        notificationProducer.sendNotification(notificationDto);
    }
}
