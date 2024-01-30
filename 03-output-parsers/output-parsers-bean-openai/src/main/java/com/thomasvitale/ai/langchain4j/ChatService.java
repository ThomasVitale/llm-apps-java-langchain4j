package com.thomasvitale.ai.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.service.ServiceOutputParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
class ChatService {

    private final ChatLanguageModel chatLanguageModel;

    ChatService(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    ArtistInfo chatWithBeanOutput(MusicQuestion question) {
        var formatInstructions = ServiceOutputParser.outputFormatInstructions(ArtistInfo.class);

        var userPromptTemplate = new PromptTemplate("""
                Tell me name and band of one musician famous for playing in a {{genre}} band.
                Consider only the musicians that play the {{instrument}} in that band.
                {{format}}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", formatInstructions);
        var prompt = userPromptTemplate.apply(model).toUserMessage();

        var chatResponse = chatLanguageModel.generate(prompt);
        return (ArtistInfo) ServiceOutputParser.parse(chatResponse, ArtistInfo.class);
    }

    List<String> chatWithListOutput(MusicQuestion question) {
        var formatInstructions = ServiceOutputParser.outputFormatInstructions(List.class);

        var userPromptTemplate = new PromptTemplate("""
                Tell me names of three musicians famous for playing in a {{genre}} band.
                Consider only the musicians that play the {{instrument}} in that band.
                {{format}}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", formatInstructions);
        var prompt = userPromptTemplate.apply(model).toUserMessage();

        var chatResponse = chatLanguageModel.generate(prompt);
        return (List<String>) ServiceOutputParser.parse(chatResponse, List.class);
    }

}
