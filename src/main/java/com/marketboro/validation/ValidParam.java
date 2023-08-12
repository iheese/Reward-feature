package com.marketboro.validation;

import com.marketboro.common.exception.CustomException;
import com.marketboro.common.exception.ErrorCode;

public class ValidParam {

    public static void pointAmount(Long pointAmount, Long requestPoint) {
        if (pointAmount < requestPoint) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT);
        }
    }
}