package com.gilpt.external;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OdsayClient {

    private final WebClient webClient;

    public OdsayClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.odsay.com").build();
    }

    // TODO: Implement methods to call the ODsay API
}
