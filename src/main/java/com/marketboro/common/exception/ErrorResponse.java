package com.marketboro.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private final ErrorCode message;

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode;
    }

    public static ResponseEntity<ErrorResponse> error(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatusCode())
                .body(ErrorResponse.builder()
                        .message(e.getErrorCode())
                        .build());
    }
}