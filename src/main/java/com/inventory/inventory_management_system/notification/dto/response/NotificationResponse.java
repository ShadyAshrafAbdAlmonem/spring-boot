package com.inventory.inventory_management_system.notification.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {
    private Long id;
    private Long recipientId;
    private String title;
    private String message;
    private String channel;
    private String status;
    private LocalDateTime createdAt;
}
