package com.reward.service;

import com.reward.domain.Point;
import com.reward.domain.PointDetail;
import com.reward.domain.User;
import com.reward.domain.enums.PointDetailStatus;
import com.reward.dto.PointRequest;
import com.reward.dto.PointResponse;
import com.reward.dto.UserResponse;
import com.reward.repository.PointDetailRepository;
import com.reward.repository.PointRepository;
import com.reward.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @InjectMocks
    private PointService pointService;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PointRepository pointRepository;
    @Mock
    private PointDetailRepository pointDetailRepository;

    Long userNo = 100L;
    User user = User.builder()
            .userNo(100L)
            .userName("홍길동")
            .pointAmount(1000L)
            .build();

    @DisplayName("포인트 적립/사용 내역 조회")
    @Test
    void getPointHistory() {
        //given
        int page = 0;
        int size = 5;
        PointRequest.PointHistory request = new PointRequest.PointHistory(userNo, page, size);
        Pageable pageable = PageRequest.of(page,size);
        List<Point> pointList = new ArrayList<>();
        List<PointResponse.PointHistory> response = pointList.stream()
                .map(point -> new PointResponse.PointHistory(point))
                .collect(Collectors.toList());

        when(userService.getUser(userNo)).thenReturn(user);
        when(pointRepository.findByUserOrderByPointNoDesc(user, pageable)).thenReturn(new PageImpl<>(pointList));

        //when
        List<PointResponse.PointHistory> result = pointService.getPointHistory(request);

        //then
        assertEquals(response, result);
    }

    @DisplayName("포인트 적립")
    @Test
    void rewardPoint() {
        //given
        Long rewardValue = 1000L;
        PointRequest.PointReward request = new PointRequest.PointReward(userNo, rewardValue);
        Point point = new Point();
        PointDetail pointDetail = new PointDetail();
        UserResponse.UserPointAmount response = new UserResponse.UserPointAmount(user);

        when(userService.getUser(userNo)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(pointRepository.save(any(Point.class))).thenReturn(point);
        when(pointDetailRepository.save(any(PointDetail.class))).thenReturn(pointDetail);

        //when
        UserResponse.UserPointAmount result = pointService.rewardPoint(request);

        //then
        assertEquals(response.getPointAmount() + rewardValue, result.getPointAmount());
    }

    @DisplayName("포인트 사용")
    @Test
    void usePoint() {
        //given
        Long pointUsage = 500L;
        PointRequest.PointUsage request = new PointRequest.PointUsage(userNo, pointUsage);
        Point point = new Point();
        List<PointDetail> pointDetailList = new ArrayList<>();
        UserResponse.UserPointAmount response = new UserResponse.UserPointAmount(user);

        when(userService.getUser(userNo)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(pointRepository.save(any(Point.class))).thenReturn(point);
        when(pointDetailRepository.findByUserAndPointDetailStatusOrderByExpDateAsc(user, PointDetailStatus.REMAIN))
                .thenReturn(pointDetailList);

        //when
        UserResponse.UserPointAmount result = pointService.usePoint(request);

        //then
        assertEquals(response.getPointAmount() - pointUsage, result.getPointAmount());
    }
}