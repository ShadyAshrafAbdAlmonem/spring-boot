package com.inventory.inventory_management_system.notification.controller;

import com.inventory.inventory_management_system.notification.dto.request.SendNotificationRequest;
import com.inventory.inventory_management_system.notification.dto.response.NotificationResponse;
import com.inventory.inventory_management_system.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> sendNotification(@Valid @RequestBody SendNotificationRequest request) {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }
}
