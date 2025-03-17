package com.company.store.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationDto {
    private Long userId;
    private String email;
    private String phone;
    private NotificationType type;
    private String message;
}
