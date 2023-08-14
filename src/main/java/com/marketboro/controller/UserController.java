package com.marketboro.controller;

import com.marketboro.dto.UserRequest;
import com.marketboro.dto.UserResponse;
import com.marketboro.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    /**
     * 유저 포인트 총액 및 데이터 조회
     *
     * @param userRequest (유저 Id)
     * @return 유저 데이터
     */
    @PostMapping("/user/getUserPoint")
    public ResponseEntity<UserResponse.UserPointAmount> getUserPoint(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity(userService.getUserPoint(userRequest.getUserNo()), HttpStatus.OK);
    }
}