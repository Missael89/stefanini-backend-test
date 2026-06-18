package com.example.demo.dto.response;

import lombok.Data;

public @Data class ResponseErrorDTO {
    private Integer code;
    private String type;
    private String message;
}
