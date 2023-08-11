package com.marketboro.dto;

import com.marketboro.domain.User;

import java.time.LocalDateTime;

public class UserResponse {

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