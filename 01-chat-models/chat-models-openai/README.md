# Chat Models: OpenAI

Text generation with LLMs via OpenAI.

## Description

LangChain4j provides a `ChatLanguageModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _LangChain4j OpenAI Spring Boot Starter_, a `ChatLanguageModel` object is autoconfigured for you to use OpenAI.
By default, the _gpt-3.5-turbo_ model is used.

```java
@RestController
class ChatController {
    private final ChatLanguageModel chatLanguageModel;

    ChatController(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    @GetMapping("/ai/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatLanguageModel.generate(message);
    }
}
```

## Running the application

The application relies on an OpenAI API for providing LLMs.

### When using OpenAI

First, make sure you have an OpenAI account.
Then, define an environment variable with the OpenAI API Key associated to your OpenAI account as the value.

```shell
export LANGCHAIN4J_OPENAI_API_KEY=<INSERT KEY HERE>
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

## Calling the application

You can now call the application that will use OpenAI and _gpt-3.5-turbo_ to generate text based on a default prompt.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/ai/chat
```

Try passing your custom prompt and check the result.

```shell
http :8080/ai/chat message=="What is the capital of Italy?"
```
