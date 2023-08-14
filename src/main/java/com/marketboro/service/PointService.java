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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final PointDetailRepository pointDetailRepository;

    /**
     * 포인트 적립/사용 내역 조회
     *
     * @param request 유저 Id, page 값 , 페이지의 size 크기
     * @return 포인트 적립/사용 내역 리스트
     */
    @Transactional(readOnly=true)
    public List<PointResponse.PointHistory> getPointHistory(PointRequest.PointHistory request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        log.info("[getPointHistory] userNO: {}, page: {}, size: {}", request.getUserNo(), request.getPage(), request.getSize());
        return pointRepository.findByUserOrderByPointNoDesc(userService.getUser(request.getUserNo()), pageable)
                .getContent()
                .stream().map(point -> new PointResponse.PointHistory(point))
                .collect(Collectors.toList());
    }

    /**
     * 포인트 적립
     *
     * @param request 유저 Id, 사용할 포인트 금액
     * @return 적립 후 유저 데이터
     */
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

        log.info("[rewardPoint] userNO: {}, rewardValue: {}", request.getUserNo(), request.getRewardValue());
        return new UserResponse.UserPointAmount(userRepository
                .save(user)
                .addPoint(request.getRewardValue()));
    }

    /**
     * 포인트 사용
     *
     * @param request (유저 Id, 사용할 포인트 금액)
     * @return 사용 후 유저 데이터
     */
    @Transactional
    public UserResponse.UserPointAmount usePoint(PointRequest.PointUsage request) {
        User user = userService.getUser(request.getUserNo());
        Long usageValue = request.getUsageValue();

        ValidParam.pointAmount(user.getPointAmount(), usageValue);
        log.info("[usePoint] pointAmount check");
        // 유저 포인트 총액 사용
        userRepository.save(user.minusPoint(usageValue));

        // 해당 포인트 사용 처리
        Point point = pointRepository.save(Point.builder()
                    .pointValue(usageValue)
                    .user(user)
                    .pointStatus(PointStatus.USE)
                    .build());

        // 남은 포인트 상세 조회
        List<PointDetail> pointDetails = pointDetailRepository.findByUserAndPointDetailStatusOrderByExpDateAsc(user, PointDetailStatus.REMAIN);

        for (PointDetail pointDetail : pointDetails) {
            if (pointDetail.getPointDetailValue() < usageValue) {
                // 사용 포인트가 클 경우 포인트 상세 사용처리
                PointDetail usedPointDetail = PointDetail.builder()
                        .pointDetailValue(pointDetail.getPointDetailValue())
                        .pointStatus(PointStatus.USE)
                        .pointDetailStatus(PointDetailStatus.USED)
                        .user(user)
                        .point(point)
                        .build();
                pointDetailRepository.save(usedPointDetail);
                pointDetailRepository.save(pointDetail.updatePointDetailStatus());
                log.info("[usePoint] point 사용 : {}", pointDetail.getPointDetailValue());
                usageValue -= pointDetail.getPointDetailValue();
            } else {
                // 잔여 포인트 상세 사용처리
                PointDetail usedPointDetail = PointDetail.builder()
                        .pointDetailValue(usageValue)
                        .pointStatus(PointStatus.USE)
                        .pointDetailStatus(PointDetailStatus.USED)
                        .user(user)
                        .point(point)
                        .build();
                pointDetailRepository.save(usedPointDetail);
                log.info("[usePoint] 잔여 point 사용: {}", usageValue);

                // 포인트 사용 후 잔여 포인트 존재하면 사용가능 상태로 저장
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
                    log.info("[usePoint] 남은 포인트 {}", pointDetail.getPointDetailValue() - usageValue);
                }
                pointDetailRepository.save(pointDetail.updatePointDetailStatus());
                break;
            }
        }
        return new UserResponse.UserPointAmount(user);
    }
}