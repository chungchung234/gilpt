package com.gilpt.external;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GptClient {

    private final WebClient webClient;

    public GptClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com").build();
    }

    // TODO: Implement methods to call the GPT API
}
