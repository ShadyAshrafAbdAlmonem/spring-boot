package com.inventory.inventory_management_system.notification.service;

import com.inventory.inventory_management_system.notification.dto.request.SendNotificationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Sends email notifications via JavaMailSender / Spring Mail.
 *
 * <p>If the request's templateData contains a "templateName" entry, the email
 * body is rendered using Thymeleaf. Otherwise the raw message from the
 * request is used as the HTML body.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public boolean send(SendNotificationRequest request) {
        log.info("Sending Email notification to {}: {}", request.getRecipientTarget(), request.getTitle());
        try {
            String to = request.getRecipientTarget();
            String subject = request.getTitle();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("noreply@example.com");

            String htmlBody;
            Map<String, Object> templateData = request.getTemplateData();

            if (templateData != null && templateData.containsKey("templateName")) {
                String templateName = (String) templateData.get("templateName");
                Context context = new Context();
                templateData.forEach((key, value) -> {
                    if (!"templateName".equals(key)) {
                        context.setVariable(key, value);
                    }
                });
                htmlBody = templateEngine.process(templateName, context);
            } else {
                // Fallback: treat the message as HTML
                String messageContent = request.getMessage();
                htmlBody = messageContent != null ? messageContent : "";
            }

            helper.setText(htmlBody, true);
            javaMailSender.send(message);
            log.info("Email notification successfully sent to {}", to);
            return true;

        } catch (MessagingException e) {
            log.error("Failed to send email notification to {}: {}", request.getRecipientTarget(), e.getMessage(), e);
            return false;
        }
    }
}
