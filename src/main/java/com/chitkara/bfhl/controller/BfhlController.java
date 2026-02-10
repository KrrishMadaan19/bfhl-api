package com.chitkara.bfhl.controller;

import com.chitkara.bfhl.model.ApiResponse;
import com.chitkara.bfhl.service.AiService;
import com.chitkara.bfhl.service.MathService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BfhlController {

    private static final String EMAIL = "your_email@chitkara.edu.in";

    @Autowired
    private MathService mathService;

    @Autowired
    private AiService aiService;

    @PostMapping("/bfhl")
    public ResponseEntity<ApiResponse> handleBFHL(@RequestBody Map<String, Object> body) {

        try {
            Object data;

            if (body.containsKey("fibonacci")) {
                int n = (int) body.get("fibonacci");
                data = mathService.fibonacci(n);
            }
            else if (body.containsKey("prime")) {
                data = mathService.prime((List<Integer>) body.get("prime"));
            }
            else if (body.containsKey("lcm")) {
                data = mathService.lcm((List<Integer>) body.get("lcm"));
            }
            else if (body.containsKey("hcf")) {
                data = mathService.hcf((List<Integer>) body.get("hcf"));
            }
            else if (body.containsKey("AI")) {
                data = aiService.askAI((String) body.get("AI"));
            }
            else {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, EMAIL, null));
            }

            return ResponseEntity.ok(
                    new ApiResponse(true, EMAIL, data)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, EMAIL, null));
        }
    }

    @GetMapping("/health")
    public ApiResponse health() {
        return new ApiResponse(true, EMAIL, null);
    }
}
