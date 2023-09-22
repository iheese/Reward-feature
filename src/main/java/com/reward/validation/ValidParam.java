package com.reward.validation;

import com.reward.common.exception.CustomException;
import com.reward.common.exception.ErrorCode;

public class ValidParam {
    /**
     * 소유한 포인트와 사용 요청 포인트를 validation 처리
     *
     * @param pointAmount 소유 포인트
     * @param requestPoint 사용 요청 포인트
     */
    public static void pointAmount(Long pointAmount, Long requestPoint) {
        if (pointAmount < requestPoint) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT);
        }
    }
}