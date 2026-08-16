package com.inventory.inventory_management_system.email.service.impl;

import com.inventory.inventory_management_system.email.dto.EmailRequest;
import com.inventory.inventory_management_system.email.dto.EmailResponse;
import com.inventory.inventory_management_system.email.entity.EmailLog;
import com.inventory.inventory_management_system.email.repository.EmailLogRepository;
import com.inventory.inventory_management_system.email.service.EmailService;
import com.inventory.inventory_management_system.email.service.TemplateService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@SuppressWarnings("null")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateService templateService;
    private final EmailLogRepository emailLogRepository;

    @Override
    public EmailResponse sendEmail(@NonNull EmailRequest request) {
        String status = "SENT";
        String error = null;

        try {
            String htmlContent = templateService.processTemplate(request.getTemplateName(), request.getTemplateVariables());
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(request.getTo());
            helper.setSubject(request.getSubject());
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", request.getTo(), e.getMessage());
            status = "FAILED";
            error = e.getMessage();
        }

        EmailLog emailLog = EmailLog.builder()
                .recipient(request.getTo())
                .subject(request.getSubject())
                .templateName(request.getTemplateName())
                .status(status)
                .errorMessage(error)
                .sentAt(LocalDateTime.now())
                .build();

        EmailLog saved = Objects.requireNonNull(emailLogRepository.save(emailLog), "Saved email log must not be null");

        return EmailResponse.builder()
                .id(saved.getId())
                .recipient(saved.getRecipient())
                .subject(saved.getSubject())
                .status(saved.getStatus())
                .sentAt(saved.getSentAt())
                .build();
    }
}