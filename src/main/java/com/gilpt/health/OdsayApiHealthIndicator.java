package com.gilpt.health;

import com.gilpt.external.OdsayClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class OdsayApiHealthIndicator implements HealthIndicator {

    private final OdsayClient odsayClient;

    public OdsayApiHealthIndicator(OdsayClient odsayClient) {
        this.odsayClient = odsayClient;
    }

    @Override
    public Health health() {
        try {
            // This is a placeholder for a real health check call to the ODsay API.
            // Replace with an actual lightweight API call.
            odsayClient.checkHealth();
            return Health.up().withDetail("message", "ODsay API is available").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("message", "ODsay API is not available").build();
        }
    }
}
