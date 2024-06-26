# Prompts Messages: Ollama

Prompting using templates with LLMs via Ollama.

## Running the application

The application relies on Ollama for providing LLMs. You can run the native Ollama app locally on your laptop (macOS or Linux), or rely on the Docker Compose/Testcontainers support in Spring Boot to spin up an Ollama service automatically at startup time.

### When using Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop (macOS or Linux).
Then, use Ollama to run the _mistral_ large language model.

```shell
ollama run mistral
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

### When using Ollama as a dev service with Docker Compose

The application can optionally rely on the native Docker Compose support in Spring Boot to spin up an Ollama service with a _mistral_ model at startup time.
To enable that, uncomment this dependency in the `build.gradle` file.

```groovy
developmentOnly "io.thomasvitale.langchain4j:langchain4j-spring-boot-docker-compose:${springLangchain4jVersion}"
```

Then, run the Spring Boot application.

```shell
./gradlew bootRun
```

### When using Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service with a _mistral_ model at startup time.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama and mistral to generate an answer to your questions.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/ai/chat/user genre="rock" instrument="piano"
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/system
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/external
```
