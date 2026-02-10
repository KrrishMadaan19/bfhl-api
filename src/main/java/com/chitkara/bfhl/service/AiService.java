package com.chitkara.bfhl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String askAI(String question) {

        try {
            // ✅ USE CORRECT GEMINI MODEL
            String url =
                    "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key="
                            + apiKey;

            // Request JSON
            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "parts", List.of(
                                            Map.of("text", question)
                                    )
                            )
                    )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> responseEntity =
                    restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            Map response = responseEntity.getBody();
            if (response == null) return "Unknown";

            List candidates = (List) response.get("candidates");
            if (candidates == null || candidates.isEmpty()) return "Unknown";

            Map candidate = (Map) candidates.get(0);
            Map content = (Map) candidate.get("content");
            List parts = (List) content.get("parts");

            String text = (String) ((Map) parts.get(0)).get("text");

            if (text == null || text.isBlank()) return "Unknown";

            // ✅ Return ONLY first word
            return text.trim().split("\\s+")[0];

        } catch (Exception e) {
            return "Unknown";
        }
    }
}
