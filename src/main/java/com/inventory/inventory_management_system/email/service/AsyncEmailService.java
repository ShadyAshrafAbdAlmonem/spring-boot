package com.inventory.inventory_management_system.email.service;

import com.inventory.inventory_management_system.email.dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncEmailService {

    private final EmailService emailService;

    @Async("taskExecutor")
    public void sendAsync(EmailRequest request) {
        log.info("Executing async email delivery to {}", request.getTo());
        try {
            emailService.sendEmail(request);
        } catch (Exception e) {
            log.error("Async email dispatch failed: {}", e.getMessage(), e);
        }
    }
}
