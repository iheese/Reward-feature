package com.marketboro.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class UserRequest {
    @Positive
    private Long userNo;
}