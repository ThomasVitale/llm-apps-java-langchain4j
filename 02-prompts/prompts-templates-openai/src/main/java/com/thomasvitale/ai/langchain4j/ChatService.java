package com.thomasvitale.ai.langchain4j;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
class ChatService {

    private final ChatLanguageModel chatLanguageModel;

    private final Resource systemMessageResource;

    ChatService(ChatLanguageModel chatLanguageModel, @Value("classpath:/prompts/system-message.st") Resource systemMessageResource) {
        this.chatLanguageModel = chatLanguageModel;
        this.systemMessageResource = systemMessageResource;
    }

    AiMessage chatWithUserMessageTemplate(MusicQuestion question) {
        var userPromptTemplate = PromptTemplate.from("""
                Tell me name and band of three musicians famous for playing in a {{genre}} band.
                Consider only the musicians that play the {{instrument}} in that band.
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre());
        var userMessage = userPromptTemplate.apply(model).toUserMessage();

        var chatResponse = chatLanguageModel.generate(userMessage);
        return chatResponse.content();
    }

    AiMessage chatWithSystemMessageTemplate(String message) {
        var systemPromptTemplate = PromptTemplate.from("""
                You are a helpful assistant that always replies starting with {{greeting}}.
                """);
        Map<String,Object> model = Map.of("greeting", randomGreeting());
        var systemMessage = systemPromptTemplate.apply(model).toSystemMessage();

        var userMessage = new UserMessage(message);

        var prompt = List.of(systemMessage, userMessage);

        var chatResponse = chatLanguageModel.generate(prompt);
        return chatResponse.content();
    }

    AiMessage chatWithSystemMessageTemplateExternal(String message) throws IOException {
        var systemPromptTemplate = PromptTemplate.from(systemMessageResource.getContentAsString(Charset.defaultCharset()));
        Map<String,Object> model = Map.of("greeting", randomGreeting());
        var systemMessage = systemPromptTemplate.apply(model).toSystemMessage();

        var userMessage = new UserMessage(message);

        var prompt = List.of(systemMessage, userMessage);

        var chatResponse = chatLanguageModel.generate(prompt);
        return chatResponse.content();
    }

    private String randomGreeting() {
        var names = List.of("Howdy", "Ahoy", "Well, well, well");
        return names.get(new Random().nextInt(names.size()));
    }

}
