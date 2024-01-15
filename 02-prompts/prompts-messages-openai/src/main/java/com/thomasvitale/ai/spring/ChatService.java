package com.thomasvitale.ai.spring;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Service
class ChatService {

    private final ChatLanguageModel chatLanguageModel;

    private final Resource systemMessageResource;

    ChatService(ChatLanguageModel chatLanguageModel, @Value("classpath:/prompts/system-message.st") Resource systemMessageResource) {
        this.chatLanguageModel = chatLanguageModel;
        this.systemMessageResource = systemMessageResource;
    }

    AiMessage chatWithSingleMessage(String message) {
        var userMessage = new UserMessage(message);
        var chatResponse = chatLanguageModel.generate(userMessage);
        return chatResponse.content();
    }

    AiMessage chatWithMultipleMessages(String message) {
        var systemMessage = new SystemMessage("""
                You are a helpful and polite assistant.
                Answer in one sentence.
                """);
        var userMessage = new UserMessage(message);
        var prompt = List.of(systemMessage, userMessage);
        var chatResponse = chatLanguageModel.generate(userMessage);
        return chatResponse.content();
    }

    AiMessage chatWithExternalMessage(String message) throws IOException {
        var systemMessage = new SystemMessage(systemMessageResource.getContentAsString(Charset.defaultCharset()));
        var userMessage = new UserMessage(message);
        var prompt = List.of(systemMessage, userMessage);
        var chatResponse = chatLanguageModel.generate(userMessage);
        return chatResponse.content();
    }

}
