# Embedding Models: OpenAI

Vector transformation (embeddings) with LLMs via Ollama.

## Description

LangChain4j provides an `EmbeddingModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _LangChain4j Ollama Spring Boot Starter_, an `EmbeddingModel` object is autoconfigured for you to use OpenAI.
By default, the _llama2_ model is used.

```java
@RestController
class EmbeddingController {
    private final EmbeddingModel embeddingModel;

    EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/ai/embed")
    String embed(@RequestParam(defaultValue = "And Gandalf yelled: 'You shall not pass!'") String message) {
        var embeddings = embeddingModel.embed(message);
        return "Size of the embedding vector: " + embeddings.content().vectorAsList().size();
    }
}
```

## Running the application

The application relies on Ollama for providing LLMs. You can run the native Ollama app locally on your laptop (macOS or Linux), or rely on the Docker Compose/Testcontainers support in Spring Boot to spin up an Ollama service automatically at startup time.

### When using Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop (macOS or Linux).
Then, use Ollama to run the _llama2_ large language model.

```shell
ollama run llama2
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

### When using Ollama as a dev service with Docker Compose

The application can optionally rely on the native Docker Compose support in Spring Boot to spin up an Ollama service with a _llama2_ model at startup time.
To enable that, uncomment this dependency in the `build.gradle` file.

```groovy
developmentOnly "io.thomasvitale.langchain4j:langchain4j-spring-boot-docker-compose:${springLangchain4jVersion}"
```

Then, run the Spring Boot application.

```shell
./gradlew bootRun
```

### When using Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service with a _llama2_ model at startup time.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama and _llama2_ to generate a vector representation (embeddings) of a default text.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/ai/embed
```

Try passing your custom prompt and check the result.

```shell
http :8080/ai/embed message=="The capital of Italy is Rome"
```
