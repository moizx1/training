package com.yolo.ai;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class ChatController {

    private final AnthropicChatModel chatModel;

    @Autowired
    public ChatController(AnthropicChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Create a unique idea for recipe generation by the chef. Using this single idea that you generate the chef can provide customer with three recipes. You may found interests or dietary restrictions in the following details which might help you only in idea generation not recipe and it must not exceed 300 characters(not words) at any cost. Here are the the details: ") String message) {
        return Map.of("generation", chatModel.call("Create a unique idea for recipe generation by the chef. Using this single idea that you generate the chef can provide customer with three recipes. You may found interests or dietary restrictions in the following details which might help you only in idea generation not recipe and it must not exceed 300 characters(not words) at any cost. Here are the the details: " + message));
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }
}