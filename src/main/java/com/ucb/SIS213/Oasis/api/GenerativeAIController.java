package com.ucb.SIS213.Oasis.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chatbot")
public class GenerativeAIController {

    @Value("${google.generativeai.api_key}")
    private String apiKey;

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody Map<String, String> requestBody) {
        String inputText = requestBody.get("inputText");
        String apiUrl = "https://api.google.dev/v1/generativeai/gemini-1.5-flash-latest/generate";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("temperature", 1);
        generationConfig.put("top_p", 0.95);
        generationConfig.put("top_k", 64);
        generationConfig.put("max_output_tokens", 8192);
        generationConfig.put("response_mime_type", "text/plain");

        Map<String, Object> safetySettings = new HashMap<>();
        safetySettings.put("category", "HARM_CATEGORY_HARASSMENT");
        safetySettings.put("threshold", "BLOCK_MEDIUM_AND_ABOVE");
        // Add other safety settings similarly...

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("model_name", "gemini-1.5-flash-latest");
        requestMap.put("generation_config", generationConfig);
        requestMap.put("safety_settings", new Map[]{safetySettings});
        requestMap.put("system_instruction", "Eres un experto en Python");
        requestMap.put("input_text", inputText);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        return response;
    }
}