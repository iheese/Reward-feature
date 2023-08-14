package com.marketboro.repository;

import com.marketboro.domain.Point;
import com.marketboro.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    /**
     * 유저의 페이지 처리된 포인트 조회
     *
     * @param user 유저
     * @param pageable
     * @return 페이지 처리된 포인트
     */
    Page<Point> findByUserOrderByPointNoDesc(User user, Pageable pageable);
}