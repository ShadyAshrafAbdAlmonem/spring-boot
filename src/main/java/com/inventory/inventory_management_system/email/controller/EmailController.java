package com.inventory.inventory_management_system.email.controller;

import com.inventory.inventory_management_system.email.dto.EmailRequest;
import com.inventory.inventory_management_system.email.dto.EmailResponse;
import com.inventory.inventory_management_system.email.service.AsyncEmailService;
import com.inventory.inventory_management_system.email.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final AsyncEmailService asyncEmailService;

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@Valid @RequestBody @NonNull EmailRequest request) {
        return ResponseEntity.ok(emailService.sendEmail(request));
    }

    @PostMapping("/send-async")
    public ResponseEntity<String> sendEmailAsync(@Valid @RequestBody @NonNull EmailRequest request) {
        asyncEmailService.sendAsync(request);
        return ResponseEntity.accepted().body("Email dispatch queued asynchronously.");
    }
}
