package com.marketboro.controller;

import com.marketboro.dto.PointRequest;
import com.marketboro.dto.PointResponse;
import com.marketboro.dto.UserResponse;
import com.marketboro.service.PointService;
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

    @PostMapping("/getPointHistory")
    public ResponseEntity<List<PointResponse.PointHistory>> getPointHistory(@Valid @RequestBody PointRequest.PointHistory request) {
        return new ResponseEntity(pointService.getPointHistory(request), HttpStatus.OK);
    }

    @PostMapping("/rewardPoint")
    public ResponseEntity<UserResponse.UserPointAmount> rewardPoint(@Valid @RequestBody PointRequest.PointReward request) {
        return new ResponseEntity(pointService.rewardPoint(request), HttpStatus.OK);
    }

    @PostMapping("/usePoint")
    public ResponseEntity<UserResponse.UserPointAmount> usePoint(@Valid @RequestBody PointRequest.PointUsage request) {
        return new ResponseEntity(pointService.usePoint(request), HttpStatus.OK);
    }
}