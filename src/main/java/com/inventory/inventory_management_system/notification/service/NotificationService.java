package com.inventory.inventory_management_system.notification.service;

import com.inventory.inventory_management_system.notification.dto.request.SendNotificationRequest;
import com.inventory.inventory_management_system.notification.dto.response.NotificationResponse;

public interface NotificationService {
    NotificationResponse sendNotification(SendNotificationRequest request);
}
