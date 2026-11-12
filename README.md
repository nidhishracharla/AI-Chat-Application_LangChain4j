# AI Chat Application

A console-based AI Chat Application built with **Core Java**, **LangChain4j**, and **Ollama**.
It accepts questions from the console, sends them to a locally running LLM via Ollama, and
displays the generated responses. The conversation continues until you type `exit`.

## Prerequisites

- Java 21+
- Maven 3.8+
- [Ollama](https://ollama.com/) installed and running

## Setup

1. Pull a model (default is `llama3.2`):

   ```bash
   ollama pull llama3.2
   ```

2. Make sure the Ollama server is running (it starts automatically on most installs, or run `ollama serve`).

## Run

```bash
mvn compile exec:java
```

Example session:

```
=== AI Chat Application (LangChain4j + Ollama) ===
Model : llama3.2
Ollama: http://localhost:11434
Type your question and press Enter. Type 'exit' to quit.

You: What is LangChain4j?
AI: LangChain4j is a Java framework for building applications powered by large language models...

You: exit
Goodbye!
```

## Configuration

| Environment variable | Default                   | Description            |
|----------------------|---------------------------|------------------------|
| `OLLAMA_BASE_URL`    | `http://localhost:11434`  | Ollama server URL      |
| `OLLAMA_MODEL`       | `llama3.2`                | Model to chat with     |

Example:

```bash
OLLAMA_MODEL=mistral mvn compile exec:java
```

## Notes

- Conversation history is kept in memory (last 20 messages) so the model has context
  across multiple questions.
