package com.thomasvitale.ai.langchain4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
class ChatController {

    private final ChatService chatService;

    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ai/chat/single")
    String chatWithSingleMessage(@RequestBody String input) {
        return chatService.chatWithSingleMessage(input).text();
    }

    @PostMapping("/ai/chat/multiple")
    String chatWithMultipleMessages(@RequestBody String input) {
        return chatService.chatWithMultipleMessages(input).text();
    }

    @PostMapping("/ai/chat/external")
    String chatWithExternalMessage(@RequestBody String input) throws IOException {
        return chatService.chatWithExternalMessage(input).text();
    }

}
