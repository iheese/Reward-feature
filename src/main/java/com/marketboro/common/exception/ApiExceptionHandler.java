package com.marketboro.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * CustomException 예외에 대한 처리
     *
     * @param e 커스텀한 예외
     * @return json 형식의 에러 데이터
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("[handleCustomException] {} : {}",e.getErrorCode().getStatusCode(), e.getErrorCode().getMessage());
        return ErrorResponse.error(e.getErrorCode());
    }
}