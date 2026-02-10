package com.chitkara.bfhl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String askAI(String question) {

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;

        Map<String, Object> request = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", question)))
                )
        );

        RestTemplate restTemplate = new RestTemplate();
        Map response = restTemplate.postForObject(url, request, Map.class);

        List candidates = (List) response.get("candidates");
        Map content = (Map) ((Map) candidates.get(0)).get("content");
        List parts = (List) content.get("parts");

        String answer = (String) ((Map) parts.get(0)).get("text");
        return answer.split(" ")[0]; // single-word response
    }
}

