package com.marketboro.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private final Integer code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getStatusCode();
        this.message = errorCode.getMessage();
    }

    public static ResponseEntity<ErrorResponse> error(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatusCode())
                .body(ErrorResponse.builder()
                        .message(e.getErrorCode().getMessage())
                        .build());
    }
}