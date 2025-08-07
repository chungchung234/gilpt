package com.gilpt.service;

import com.gilpt.dto.ChatRequest;
import com.gilpt.dto.ChatResponse;
import com.gilpt.dto.openai.OpenAiChatRequest;
import com.gilpt.dto.openai.OpenAiChatResponse;
import com.gilpt.external.GptClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final GptClient gptClient;

    public ChatService(GptClient gptClient) {
        this.gptClient = gptClient;
    }

    public ChatResponse getChatResponse(ChatRequest request) {
        OpenAiChatRequest.Message userMessage = new OpenAiChatRequest.Message("user", request.getMessage());
        OpenAiChatRequest openAiRequest = new OpenAiChatRequest("gpt-3.5-turbo", List.of(userMessage), 0.7);

        return gptClient.getChatResponse(openAiRequest)
                .map(response -> {
                    String content = response.choices().get(0).message().content();
                    return new ChatResponse(content);
                })
                .block(); // Using block() for simplicity, consider async handling in a real application
    }
}
