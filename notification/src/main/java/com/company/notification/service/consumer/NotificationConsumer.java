package com.company.notification.service.consumer;

import com.company.notification.dto.NotificationDto;
import com.company.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void handleNotification(NotificationDto notificationDto) {
        notificationService.createNotification(notificationDto);
    }
}
