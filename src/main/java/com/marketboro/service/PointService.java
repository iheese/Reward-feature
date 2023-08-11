package com.marketboro.service;

import com.marketboro.common.exception.CustomException;
import com.marketboro.common.exception.ErrorCode;
import com.marketboro.domain.Point;
import com.marketboro.domain.PointDetail;
import com.marketboro.domain.enums.PointDetailStatus;
import com.marketboro.domain.enums.PointStatus;
import com.marketboro.domain.User;
import com.marketboro.dto.PointRequest;
import com.marketboro.dto.PointResponse;
import com.marketboro.dto.UserResponse;
import com.marketboro.repository.PointDetailRepository;
import com.marketboro.repository.PointRepository;
import com.marketboro.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PointService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final PointDetailRepository pointDetailRepository;

    @Transactional(readOnly=true)
    public List<PointResponse.PointHistory> getPointHistory(PointRequest.PointHistory request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return pointRepository.findByUserOrderByIdDesc(userService.getUser(request.getUserNo()), pageable)
                .getContent()
                .stream().map(point -> new PointResponse.PointHistory(point))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse.UserPointAmount rewardPoint(PointRequest.PointReward request) {
        Point point = pointRepository.save(Point.builder()
                .pointValue(request.getRewardValue())
                .user(userService.getUser(request.getUserNo()))
                .pointStatus(PointStatus.REWARD)
                .build());

        pointDetailRepository.save(PointDetail.builder()
                .pointDetailValue(point.getPointValue())
                .pointStatus(PointStatus.REWARD)
                .pointDetailStatus(PointDetailStatus.REMAIN)
                .expDate(point.getExpDate())
                .point(point)
                .build());

        return new UserResponse.UserPointAmount(userRepository
                .save(userService.getUser(request.getUserNo())
                .addPoint(request.getRewardValue())));
    }

    @Transactional
    public UserResponse.UserPointAmount usePoint(PointRequest.PointUsage request) {
        User user = userService.getUser(request.getUserNo());
        Long usageValue = request.getUsageValue();

        if(user.getPointAmount() >= request.getUsageValue()) {
            userRepository.save(user.minusPoint(usageValue));

            Point point = pointRepository.save(Point.builder()
                    .pointValue(usageValue)
                    .user(user)
                    .pointStatus(PointStatus.USE)
                    .build());

            List<PointDetail> pointDetails = pointDetailRepository.findByUserAndPointDetailStatusEqualsOrderByIdAsc(user, PointDetailStatus.REMAIN);

            for (PointDetail pointDetail : pointDetails) {
                if (pointDetail.getPointDetailValue() < usageValue) {
                    PointDetail usedPointDetail = PointDetail.builder()
                            .pointDetailValue(pointDetail.getPointDetailValue())
                            .pointStatus(PointStatus.USE)
                            .pointDetailStatus(PointDetailStatus.USED)
                            .point(point)
                            .build();
                    pointDetailRepository.save(usedPointDetail);
                    usageValue -= pointDetail.getPointDetailValue();
                } else {
                    PointDetail usedPointDetail = PointDetail.builder()
                            .pointDetailValue(usageValue)
                            .pointStatus(PointStatus.USE)
                            .pointDetailStatus(PointDetailStatus.USED)
                            .point(point)
                            .build();
                    pointDetailRepository.save(usedPointDetail);
                }
            }
            if(usageValue > 0) {
                PointDetail remainPointDetail = PointDetail.builder()
                        .pointDetailValue(usageValue)
                        .expDate(point.getExpDate())
                        .pointStatus(PointStatus.USE)
                        .pointDetailStatus(PointDetailStatus.REMAIN)
                        .point(point)
                        .build();
                pointDetailRepository.save(remainPointDetail);
            }
        } else {
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT);
        }

        return new UserResponse.UserPointAmount(user);
    }
}