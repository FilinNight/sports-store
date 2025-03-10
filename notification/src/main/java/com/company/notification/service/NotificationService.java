package com.company.notification.service;

import com.company.notification.dto.NotificationDto;

public interface NotificationService {

    void sendNotification(NotificationDto notification);
}
