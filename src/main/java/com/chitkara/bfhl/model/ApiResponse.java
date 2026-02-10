package com.chitkara.bfhl.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean is_success;
    private String official_email;
    private Object data;
}
