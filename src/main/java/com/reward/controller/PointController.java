package com.reward.controller;

import com.reward.dto.PointRequest;
import com.reward.dto.PointResponse;
import com.reward.dto.UserResponse;
import com.reward.service.PointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/point")
@RequiredArgsConstructor
@RestController
public class PointController {
    private final PointService pointService;

    /**
     * 포인트 적립/사용 조회
     *
     * @param request 유저 Id, page 값 , 페이지의 size 크기
     * @return 포인트 적립/사용 내역 리스트
     */
    @PostMapping("/getPointHistory")
    public ResponseEntity<List<PointResponse.PointHistory>> getPointHistory(@Valid @RequestBody PointRequest.PointHistory request) {
        return new ResponseEntity(pointService.getPointHistory(request), HttpStatus.OK);
    }

    /**
     * 포인트 적립
     *
     * @param request 유저 Id, 사용할 포인트 금액
     * @return 적립 후 유저 데이터
     */
    @PostMapping("/rewardPoint")
    public ResponseEntity<UserResponse.UserPointAmount> rewardPoint(@Valid @RequestBody PointRequest.PointReward request) {
        return new ResponseEntity(pointService.rewardPoint(request), HttpStatus.OK);
    }

    /**
     * 포인트 사용
     *
     * @param request (유저 Id, 사용할 포인트 금액)
     * @return 사용 후 유저 데이터
     */
    @PostMapping("/usePoint")
    public ResponseEntity<UserResponse.UserPointAmount> usePoint(@Valid @RequestBody PointRequest.PointUsage request) {
        return new ResponseEntity(pointService.usePoint(request), HttpStatus.OK);
    }
}