package com.inventory.inventory_management_system.scheduler.job;

import com.inventory.inventory_management_system.notification.dto.request.SendNotificationRequest;
import com.inventory.inventory_management_system.notification.service.EmailNotificationService;
import com.inventory.inventory_management_system.product.entity.Product;
import com.inventory.inventory_management_system.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExpiredProductsJob {

    private final ProductRepository productRepository;
    private final EmailNotificationService emailNotificationService;

    @Value("${notification.admin.email:admin@default.com}")
    private String adminEmail;

    // Runs every day at 01:00 AM
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void checkExpiredProducts() {
        log.info("Checking for expired inventory items...");
        
        try {
            // Query products where expiry date is before today and product is still active
            List<Product> expiredProducts = productRepository.findExpiredProducts();
            
            if (expiredProducts.isEmpty()) {
                log.info("No expired products found.");
                return;
            }

            log.info("Found {} expired products", expiredProducts.size());

            // Mark products as inactive
            for (Product product : expiredProducts) {
                product.setActive(false);
                productRepository.save(product);
                log.warn("Product '{}' (ID: {}) marked as expired and deactivated. Expiry date: {}", 
                        product.getName(), product.getId(), product.getExpiryDate());
            }

            // Send notification to admins
            sendExpiryNotification(expiredProducts);
            
            log.info("Expired products check completed. {} products deactivated.", expiredProducts.size());

        } catch (Exception e) {
            log.error("Error checking expired products: {}", e.getMessage(), e);
        }
    }

    private void sendExpiryNotification(List<Product> expiredProducts) {
        try {
            StringBuilder message = new StringBuilder();
            message.append("The following products have expired and been deactivated:\n\n");
            
            for (Product product : expiredProducts) {
                message.append("- ").append(product.getName())
                       .append(" (SKU: ").append(product.getSku())
                       .append(") - Expired on: ").append(product.getExpiryDate())
                       .append("\n");
            }

            SendNotificationRequest request = SendNotificationRequest.builder()
                    .recipientTarget(adminEmail)
                    .title("Expired Products Alert")
                    .message(message.toString())
                    .build();

            boolean sent = emailNotificationService.send(request);
            if (sent) {
                log.info("Expiry notification sent to admin");
            } else {
                log.error("Failed to send expiry notification to admin");
            }

        } catch (Exception e) {
            log.error("Error sending expiry notification: {}", e.getMessage(), e);
        }
    }
}