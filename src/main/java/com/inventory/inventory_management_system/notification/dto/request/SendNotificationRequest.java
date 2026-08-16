package com.inventory.inventory_management_system.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SendNotificationRequest {

    @NotNull(message = "Recipient ID is required")
    private Long recipientId;

    private String recipientTarget; // Email address, Phone number, or Device token

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Message or template name is required")
    private String message;

    @NotBlank(message = "Channel is required")
    private String channel; // EMAIL, SMS, PUSH

    private Map<String, Object> templateData;
}
