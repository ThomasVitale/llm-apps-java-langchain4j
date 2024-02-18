package com.thomasvitale.ai.langchain4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
class ChatController {

    private final ChatService chatService;

    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ai/chat/simple")
    String chatWithText(@RequestBody String input) {
        return chatService.chatWithText(input);
    }

    @PostMapping("/ai/chat/prompt")
    String chatWithPrompt(@RequestBody String input) {
        return chatService.chatWithPrompt(input).content().text();
    }

    @PostMapping("/ai/chat/full")
    Map<String,Object> chatWithPromptAndFullResponse(@RequestBody String message) {
        var chatResponse = chatService.chatWithPrompt(message);
        return Map.of(
                "content", Map.of(
                        "message", chatResponse.content().text(),
                        "type", chatResponse.content().type()
                ),
                "tokenUsage", Map.of(
                        "inputTokenCount", Objects.requireNonNullElse(chatResponse.tokenUsage().inputTokenCount(), 0), // Ollama might not populate this field
                        "outputTokenCount", chatResponse.tokenUsage().outputTokenCount(),
                        "totalTokenCount", chatResponse.tokenUsage().totalTokenCount()
                ),
                "finishReason", Objects.requireNonNullElse(chatResponse.finishReason(), "unknown") // Ollama might not populate this field
        );
    }

}
