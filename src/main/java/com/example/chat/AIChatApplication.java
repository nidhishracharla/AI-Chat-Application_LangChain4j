package com.example.chat;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AIChatApplication {

    private static final String OLLAMA_BASE_URL =
            System.getenv().getOrDefault("OLLAMA_BASE_URL", "http://localhost:11434");
    private static final String MODEL_NAME =
            System.getenv().getOrDefault("OLLAMA_MODEL", "llama3.2");

    public static void main(String[] args) {
        ChatModel model = OllamaChatModel.builder()
                .baseUrl(OLLAMA_BASE_URL)
                .modelName(MODEL_NAME)
                .temperature(0.7)
                .build();

        ChatMemory memory = MessageWindowChatMemory.withMaxMessages(20);

        System.out.println("=== AI Chat Application (LangChain4j + Ollama) ===");
        System.out.println("Model : " + MODEL_NAME);
        System.out.println("Ollama: " + OLLAMA_BASE_URL);
        System.out.println("Type your question and press Enter. Type 'exit' to quit.\n");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("You: ");
                if (!scanner.hasNextLine()) {
                    break;
                }

                String question = scanner.nextLine().trim();
                if (question.isEmpty()) {
                    continue;
                }
                if (question.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }

                UserMessage userMessage = UserMessage.from(question);
                List<ChatMessage> messages = new ArrayList<>(memory.messages());
                messages.add(userMessage);

                try {
                    ChatResponse response = model.chat(
                            ChatRequest.builder().messages(messages).build());
                    AiMessage answer = response.aiMessage();

                    memory.add(userMessage);
                    memory.add(answer);

                    System.out.println("AI: " + answer.text() + "\n");
                } catch (Exception e) {
                    System.err.println("Error communicating with Ollama: " + e.getMessage());
                    System.err.println("Make sure Ollama is running and the model '" + MODEL_NAME
                            + "' is pulled (ollama pull " + MODEL_NAME + ").\n");
                }
            }
        }
    }
}
