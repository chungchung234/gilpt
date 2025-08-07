package com.gilpt.health;

import com.gilpt.external.GptClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class GptApiHealthIndicator implements HealthIndicator {

    private final GptClient gptClient;

    public GptApiHealthIndicator(GptClient gptClient) {
        this.gptClient = gptClient;
    }

    @Override
    public Health health() {
        try {
            // This is a placeholder for a real health check call to the GPT API.
            // Replace with an actual lightweight API call.
            gptClient.checkHealth();
            return Health.up().withDetail("message", "GPT API is available").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("message", "GPT API is not available").build();
        }
    }
}
