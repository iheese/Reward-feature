package com.reward.controller;

import com.reward.dto.UserResponse;
import com.reward.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    /**
     * 유저 포인트 총액 및 데이터 조회
     *
     * @param userNo (유저 Id)
     * @return 유저 데이터
     */
    @GetMapping("/point/{userNo}")
    public ResponseEntity<UserResponse.UserPointAmount> getUserPoint(@Valid @PathVariable("userNo") Long userNo) {
        return new ResponseEntity(userService.getUserPoint(userNo), HttpStatus.OK);
    }
}