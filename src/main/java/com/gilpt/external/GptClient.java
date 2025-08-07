package com.gilpt.external;

import com.gilpt.config.ApiKeysConfig;
import com.gilpt.dto.openai.OpenAiChatRequest;
import com.gilpt.dto.openai.OpenAiChatResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GptClient {

    private final WebClient webClient;
    private final ApiKeysConfig apiKeys;

    public GptClient(WebClient.Builder webClientBuilder, ApiKeysConfig apiKeys) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com").build();
        this.apiKeys = apiKeys;
    }

    public Mono<OpenAiChatResponse> getChatResponse(OpenAiChatRequest request) {
        return webClient.post()
                .uri("/v1/chat/completions")
                .headers(headers -> headers.setBearerAuth(apiKeys.gpt()))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OpenAiChatResponse.class);
    }

    public void checkHealth() {
        // A simple, lightweight call to check the API status.
        webClient.get()
                .uri("/v1/models")
                .headers(headers -> headers.setBearerAuth(apiKeys.gpt()))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
