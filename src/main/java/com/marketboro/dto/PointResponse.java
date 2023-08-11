package com.marketboro.dto;

import com.marketboro.domain.Point;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PointResponse {
    public static class PointHistory {
        private Long pointNo;
        private Long pointValue;
        private LocalDateTime regDate;
        private LocalDateTime expDate;
        private LocalDateTime useDate;

        public PointHistory(Point point) {
            this.pointNo = point.getPointNo();
            this.pointValue = point.getPointValue();
            this.regDate = point.getRegDate();
            this.expDate = point.getExpDate();
        }
    }
}