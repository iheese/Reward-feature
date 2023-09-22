package com.reward.validation;

import com.reward.common.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidParamTest {

    @DisplayName("보유 포인트와 요청 포인트 validation")
    @Test
    void pointAmount() {
        //given
        Long lessPointAmount = 1000L;
        Long requestPoint = 2000L;
        Long morePointAmount = 3000L;

        //when
        //then
        assertThrows(CustomException.class, () -> { ValidParam.pointAmount(lessPointAmount, requestPoint);});
        assertDoesNotThrow(() -> { ValidParam.pointAmount(morePointAmount, requestPoint);});
    }
}