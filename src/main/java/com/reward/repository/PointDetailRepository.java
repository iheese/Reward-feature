package com.reward.repository;

import com.reward.domain.PointDetail;
import com.reward.domain.User;
import com.reward.domain.enums.PointDetailStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointDetailRepository extends JpaRepository<PointDetail, Long> {
    /**
     * 유저의 포인트 상세 데이터를 일치하는 이넘값에 대해 유효기간이 빠른 순으로 조회
     *
     * @param user 유저
     * @param pointDetailStatus 포인트 상세 상태
     * @return 포인트 상세 데이터
     */
    List<PointDetail> findByUserAndPointDetailStatusOrderByExpDateAsc(User user, PointDetailStatus pointDetailStatus);
}