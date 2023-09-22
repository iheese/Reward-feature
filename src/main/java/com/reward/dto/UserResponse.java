package com.reward.dto;

import com.reward.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserResponse {
    @Getter
    public static class UserPointAmount {
        private Long userNo;
        private String userName;
        private LocalDateTime regDate;
        private Long pointAmount;

        public UserPointAmount(User user) {
            this.userNo = user.getUserNo();
            this.userName = user.getUserName();
            this.regDate = user.getRegDate();
            this.pointAmount = user.getPointAmount();
        }
    }
}