package com.thomasvitale.ai.langchain4j;

import io.thomasvitale.langchain4j.testcontainers.service.containers.OllamaContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;

@TestConfiguration(proxyBeanMethods = false)
public class TestChatModelsOllamaApplication {

    @Bean
    @RestartScope
    @ServiceConnection
    OllamaContainer ollama() {
        return new OllamaContainer("ghcr.io/thomasvitale/ollama-llama2");
    }

    public static void main(String[] args) {
        SpringApplication.from(ChatModelsOllamaApplication::main).with(TestChatModelsOllamaApplication.class).run(args);
    }

}
