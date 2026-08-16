package com.inventory.inventory_management_system.config;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Date;

/**
 * Development-only Firebase initializer.
 * <p>
 * Provides a {@link FirebaseMessaging} bean using dummy credentials so the
 * application can start without real Google Cloud credentials.  In production
 * the credentials would be supplied via a service-account JSON file or
 * environment variable.
 */
@Configuration
@Profile("dev")
@Slf4j
public class FirebaseInitializer {

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        try {
            AccessToken token = new AccessToken("dev-dummy-token",
                    new Date(System.currentTimeMillis() + 3_600_000L));
            GoogleCredentials credentials = GoogleCredentials.create(token);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .setProjectId("dev-test-project")
                    .build();

            FirebaseApp app = FirebaseApp.initializeApp(options, "dev-firebase-app");
            log.info("Firebase initialized in dev mode with dummy credentials (no real FCM delivery)");
            return FirebaseMessaging.getInstance(app);
        } catch (Exception e) {
            log.warn("Failed to initialize Firebase with dummy credentials: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize Firebase in dev mode", e);
        }
    }
}
