package com.marketboro.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND_USER(1000, "주어진 id 값의 회원을 조회할 수 없습니다.");

    private final Integer statusCode;
    private final String message;
}