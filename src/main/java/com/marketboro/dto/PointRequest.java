package com.marketboro.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

public class PointRequest {
    @Getter
    public static class PointHistory {
        @Positive
        private Long userNo;
        private int page;
        private int size;
    }

    @Getter
    public static class PointReward {
        @Positive
        private Long userNo;
        @Positive
        private Long rewardValue;
    }

    @Getter
    public static class PointUsage {
        @Positive
        private Long userNo;
        @Positive
        private Long usageValue;
    }
}