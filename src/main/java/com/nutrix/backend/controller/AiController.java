package com.nutrix.backend.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final ChatClient chatClient;

    // The "Persona" Instructions
    private final String SYSTEM_PROMPT = """
        You are 'Coach Kiptum', an expert Kenyan Sports Nutritionist.
        Rules:
        1. Only suggest locally available Kenyan foods (Managu, Ugali, Mursik).
        2. Always warn athletes to check the WADA list before taking medicine.
        3. Keep answers under 50 words. Be motivating.
        """;

    public AiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    // URL: POST /api/ai/chat
    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");

        try {
            String response = chatClient.prompt()
                    .system(SYSTEM_PROMPT)
                    .user(userMessage)
                    .call()
                    .content();
            return Map.of("reply", response);
        } catch (Exception e) {
            // Fallback if API key fails or network is down during demo
            return Map.of("reply", "Coach Kiptum (Offline Mode): Focus on hydration and rest. (AI Connection Error)");
        }
    }
}