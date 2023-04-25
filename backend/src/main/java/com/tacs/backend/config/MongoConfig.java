package com.tacs.backend.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {
    @Bean
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .retryWrites(true)
                .applyToClusterSettings(builder ->
                        builder.serverSelectionTimeout(5000, TimeUnit.MILLISECONDS))
                .applyToConnectionPoolSettings((ConnectionPoolSettings.Builder builder) -> builder.maxSize(300)
                        .minSize(100)
                        .maxConnectionLifeTime(1000, TimeUnit.MILLISECONDS)
                        .maintenanceFrequency(5000, TimeUnit.MILLISECONDS)
                        .maxConnectionIdleTime(1000, TimeUnit.MILLISECONDS)
                        .maxWaitTime(150, TimeUnit.MILLISECONDS))
                .applyToSocketSettings(builder -> builder.connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS))
                .applicationName("BackendApplication")
                .build();
    }
}
