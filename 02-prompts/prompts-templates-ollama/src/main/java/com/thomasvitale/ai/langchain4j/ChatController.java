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

    @PostMapping("/ai/chat/user")
    String chatWithUserMessageTemplate(@RequestBody MusicQuestion question) {
        return chatService.chatWithUserMessageTemplate(question).text();
    }

    @PostMapping("/ai/chat/system")
    String chatWithSystemMessageTemplate(@RequestBody String question) {
        return chatService.chatWithSystemMessageTemplate(question).text();
    }

    @PostMapping("/ai/chat/external")
    String chatWithSystemMessageTemplateExternal(@RequestBody String question) throws IOException {
        return chatService.chatWithSystemMessageTemplateExternal(question).text();
    }

}
