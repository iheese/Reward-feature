package com.reward.common.exception;

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

    /**
     * 커스텀 에러 코드를 받아 ResponseEntity 리턴
     *
     * @param errorCode 커스텀 에러 코드
     * @return 에러 데이터 ResponseEntity
     */
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