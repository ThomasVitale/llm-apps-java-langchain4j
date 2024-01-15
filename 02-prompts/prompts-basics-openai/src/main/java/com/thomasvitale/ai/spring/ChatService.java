package com.thomasvitale.ai.spring;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.springframework.stereotype.Service;

@Service
class ChatService {

    private final ChatLanguageModel chatLanguageModel;

    ChatService(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    String chatWithText(String message) {
        return chatLanguageModel.generate(message);
    }

    Response<AiMessage> chatWithPrompt(String message) {
        return chatLanguageModel.generate(new UserMessage(message));
    }

}
