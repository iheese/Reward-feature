package com.marketboro.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND_USER(100, "주어진 id 값의 회원을 조회할 수 없습니다."),
    NOT_NUMBER(101, "주어진 id 값이 올바르지 않습니다."),
    NOT_ENOUGH_POINT(102, "사용하시려는 포인트가 소유한 포인트보다 많습니다.");

    private final Integer statusCode;
    private final String message;
}