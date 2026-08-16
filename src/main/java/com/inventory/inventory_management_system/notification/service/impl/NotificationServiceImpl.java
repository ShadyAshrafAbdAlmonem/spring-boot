package com.inventory.inventory_management_system.notification.service.impl;

import com.inventory.inventory_management_system.notification.dto.request.SendNotificationRequest;
import com.inventory.inventory_management_system.notification.dto.response.NotificationResponse;
import com.inventory.inventory_management_system.notification.entity.Notification;
import com.inventory.inventory_management_system.notification.repository.NotificationRepository;
import com.inventory.inventory_management_system.notification.service.NotificationService;
import com.inventory.inventory_management_system.notification.service.EmailNotificationService;
import com.inventory.inventory_management_system.notification.service.PushNotificationService;
import com.inventory.inventory_management_system.notification.service.SmsNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailNotificationService emailNotificationService;
    private final PushNotificationService pushNotificationService;
    private final SmsNotificationService smsNotificationService;

    @Override
    public NotificationResponse sendNotification(SendNotificationRequest request) {
        boolean sent = false;

        switch (request.getChannel().toUpperCase()) {
            case "EMAIL" -> sent = emailNotificationService.send(request);
            case "PUSH" -> sent = pushNotificationService.send(request);
            case "SMS" -> sent = smsNotificationService.send(request);
            default -> throw new IllegalArgumentException("Unsupported notification channel: " + request.getChannel());
        }

        Notification notification = Notification.builder()
                .recipientId(request.getRecipientId())
                .title(request.getTitle())
                .message(request.getMessage())
                .channel(request.getChannel())
                .status(sent ? "SENT" : "FAILED")
                .build();

        Notification saved = Objects.requireNonNull(notificationRepository.save(notification), "Saved notification must not be null");

        return NotificationResponse.builder()
                .id(saved.getId())
                .recipientId(saved.getRecipientId())
                .title(saved.getTitle())
                .message(saved.getMessage())
                .channel(saved.getChannel())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}