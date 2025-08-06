package com.gilpt.service;

import com.gilpt.dto.ChatRequest;
import com.gilpt.dto.ChatResponse;
import com.gilpt.external.GptClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final GptClient gptClient;

    public ChatService(GptClient gptClient) {
        this.gptClient = gptClient;
    }

    public ChatResponse getChatResponse(ChatRequest request) {
        // TODO: Implement GPT API call
        return new ChatResponse("This is a dummy reply from the service.");
    }
}
