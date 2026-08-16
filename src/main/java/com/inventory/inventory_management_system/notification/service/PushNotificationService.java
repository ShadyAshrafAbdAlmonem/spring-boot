package com.inventory.inventory_management_system.notification.service;

import com.inventory.inventory_management_system.notification.dto.request.SendNotificationRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Sends push notifications via Firebase Cloud Messaging (FCM)
 * (APNS is handled transparently by FCM for iOS devices).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PushNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public boolean send(SendNotificationRequest request) {
        log.info("Sending Push notification to target {}: {}", request.getRecipientTarget(), request.getTitle());
        try {
            Notification notification = Notification.builder()
                    .setTitle(request.getTitle())
                    .setBody(request.getMessage())
                    .build();

            Message message = Message.builder()
                    .setToken(request.getRecipientTarget())
                    .setNotification(notification)
                    .build();

            String response = firebaseMessaging.send(message);
            log.info("Push notification successfully sent to {}. Firebase response: {}", request.getRecipientTarget(), response);
            return true;

        } catch (Exception e) {
            log.error("Failed to send push notification to {}: {}", request.getRecipientTarget(), e.getMessage(), e);
            return false;
        }
    }
}
