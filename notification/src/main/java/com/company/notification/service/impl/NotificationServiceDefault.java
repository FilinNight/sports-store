package com.company.notification.service.impl;

import com.company.notification.dto.NotificationDto;
import com.company.notification.model.Notification;
import com.company.notification.model.NotificationStatus;
import com.company.notification.repository.NotificationRepository;
import com.company.notification.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceDefault implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void createNotification(NotificationDto notificationDto) {
        Notification notification = toNotification(notificationDto);
        notification.setStatus(NotificationStatus.PENDING);
        notificationRepository.save(notification);
    }

    private Notification toNotification(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setUserId(dto.getUserId());
        notification.setType(dto.getType());
        notification.setEmail(dto.getEmail());
        notification.setPhone(dto.getPhone());
        notification.setMessage(dto.getMessage());
        return notification;
    }
}
