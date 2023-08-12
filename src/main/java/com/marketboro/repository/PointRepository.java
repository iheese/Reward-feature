package com.marketboro.repository;

import com.marketboro.domain.Point;
import com.marketboro.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    Page<Point> findByUserOrderByPointNoDesc(User user, Pageable pageable);
}