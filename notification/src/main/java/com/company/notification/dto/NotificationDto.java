package com.company.notification.dto;

import com.company.notification.model.NotificationType;
import lombok.Getter;

@Getter
public class NotificationDto {
    private Long userId;
    private String email;
    private String phone;
    private NotificationType type;
    private String message;
}
