package com.marketboro.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final String name;
    private final String message;
    private final LocalDateTime timestamp;

    public static ResponseEntity<ErrorResponse> error(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ErrorResponse.builder()
                        .name(errorCode.name())
                        .message(errorCode.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}