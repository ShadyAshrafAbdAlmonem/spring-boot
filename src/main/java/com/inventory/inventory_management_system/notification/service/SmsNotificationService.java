package com.inventory.inventory_management_system.notification.service;

import com.inventory.inventory_management_system.notification.dto.request.SendNotificationRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Sends SMS notifications via Twilio (or any configurable SMS provider SDK).
 */
@Service
@Slf4j
public class SmsNotificationService {

    private final String fromPhoneNumber;

    public SmsNotificationService(@Value("${notification.twilio.phone-from:+1234567890}") String fromPhoneNumber) {
        this.fromPhoneNumber = fromPhoneNumber;
    }

    public boolean send(SendNotificationRequest request) {
        log.info("Sending SMS notification to {}: {}", request.getRecipientTarget(), request.getMessage());
        try {
            PhoneNumber to = new PhoneNumber(request.getRecipientTarget());
            PhoneNumber from = new PhoneNumber(fromPhoneNumber);

            Message message = Message.creator(
                    to,
                    from,
                    request.getMessage()
            ).create();

            log.info("SMS notification successfully sent to {}. Message SID: {}", request.getRecipientTarget(), message.getSid());
            return true;

        } catch (Exception e) {
            log.error("Failed to send SMS notification to {}: {}", request.getRecipientTarget(), e.getMessage(), e);
            return false;
        }
    }
}
