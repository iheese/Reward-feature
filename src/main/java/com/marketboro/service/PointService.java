package com.marketboro.service;

import com.marketboro.domain.Point;
import com.marketboro.domain.PointDetail;
import com.marketboro.domain.User;
import com.marketboro.domain.enums.PointDetailStatus;
import com.marketboro.domain.enums.PointStatus;
import com.marketboro.dto.PointRequest;
import com.marketboro.dto.PointResponse;
import com.marketboro.dto.UserResponse;
import com.marketboro.repository.PointDetailRepository;
import com.marketboro.repository.PointRepository;
import com.marketboro.repository.UserRepository;
import com.marketboro.validation.ValidParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final PointDetailRepository pointDetailRepository;

    @Transactional(readOnly=true)
    public List<PointResponse.PointHistory> getPointHistory(PointRequest.PointHistory request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return pointRepository.findByUserOrderByPointNoDesc(userService.getUser(request.getUserNo()), pageable)
                .getContent()
                .stream().map(point -> new PointResponse.PointHistory(point))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse.UserPointAmount rewardPoint(PointRequest.PointReward request) {
        User user = userService.getUser(request.getUserNo());
        Point point = pointRepository.save(Point.builder()
                .pointValue(request.getRewardValue())
                .user(user)
                .pointStatus(PointStatus.REWARD)
                .build());

        pointDetailRepository.save(PointDetail.builder()
                .pointDetailValue(point.getPointValue())
                .pointStatus(PointStatus.REWARD)
                .pointDetailStatus(PointDetailStatus.REMAIN)
                .expDate(point.getExpDate())
                .user(user)
                .point(point)
                .build());

        return new UserResponse.UserPointAmount(userRepository
                .save(user)
                .addPoint(request.getRewardValue()));
    }

    public UserResponse.UserPointAmount usePoint(PointRequest.PointUsage request) {
        User user = userService.getUser(request.getUserNo());
        Long usageValue = request.getUsageValue();

        ValidParam.pointAmount(user.getPointAmount(), usageValue);
        userRepository.save(user.minusPoint(usageValue));

        Point point = pointRepository.save(Point.builder()
                    .pointValue(usageValue)
                    .user(user)
                    .pointStatus(PointStatus.USE)
                    .build());

        List<PointDetail> pointDetails = pointDetailRepository.findByUserAndPointDetailStatusOrderByExpDateAsc(user, PointDetailStatus.REMAIN);

        for (PointDetail pointDetail : pointDetails) {
            if (pointDetail.getPointDetailValue() < usageValue) {
                PointDetail usedPointDetail = PointDetail.builder()
                        .pointDetailValue(pointDetail.getPointDetailValue())
                        .pointStatus(PointStatus.USE)
                        .pointDetailStatus(PointDetailStatus.USED)
                        .user(user)
                        .point(point)
                        .build();
                pointDetailRepository.save(usedPointDetail);
                pointDetailRepository.save(pointDetail.updatePointDetailStatus());
                usageValue -= pointDetail.getPointDetailValue();
            } else {
                PointDetail usedPointDetail = PointDetail.builder()
                        .pointDetailValue(usageValue)
                        .pointStatus(PointStatus.USE)
                        .pointDetailStatus(PointDetailStatus.USED)
                        .user(user)
                        .point(point)
                        .build();
                pointDetailRepository.save(usedPointDetail);

                if(pointDetail.getPointDetailValue() - usageValue > 0) {
                    PointDetail remainPointDetail = PointDetail.builder()
                            .pointDetailValue(pointDetail.getPointDetailValue() - usageValue)
                            .expDate(pointDetail.getExpDate())
                            .pointStatus(PointStatus.USE)
                            .pointDetailStatus(PointDetailStatus.REMAIN)
                            .user(user)
                            .point(point)
                            .build();
                    pointDetailRepository.save(remainPointDetail);
                }
                pointDetailRepository.save(pointDetail.updatePointDetailStatus());
                break;
            }
        }
        return new UserResponse.UserPointAmount(user);
    }
}