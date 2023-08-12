package com.marketboro.dto;

import com.marketboro.domain.Point;
import com.marketboro.domain.enums.PointStatus;
import lombok.Getter;

import java.time.LocalDateTime;

public class PointResponse {
    @Getter
    public static class PointHistory {
        private Long pointNo;
        private Long pointValue;
        private PointStatus pointStatus;
        private LocalDateTime regDate;
        private LocalDateTime expDate;

        public PointHistory(Point point) {
            this.pointNo = point.getPointNo();
            this.pointValue = point.getPointValue();
            this.pointStatus = point.getPointStatus();
            this.regDate = point.getRegDate();
            this.expDate = point.getExpDate();
        }
    }
}