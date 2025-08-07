package com.gilpt.external;

import com.gilpt.config.ApiKeysConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OdsayClient {

    private final WebClient webClient;
    private final ApiKeysConfig apiKeys;

    public OdsayClient(WebClient.Builder webClientBuilder, ApiKeysConfig apiKeys) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.odsay.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION,"Bearer "+apiKeys)
                .build();
        this.apiKeys = apiKeys;
    }

    public void checkHealth() {
        // A simple, lightweight call to check the API status.
        // This might need to be adjusted based on the actual ODsay API.
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/api/searchStation")
                        .queryParam("stationName", "서울역")
                        .build())
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    // TODO: Implement methods to call the ODsay API using apiKeys.odsay()
}
