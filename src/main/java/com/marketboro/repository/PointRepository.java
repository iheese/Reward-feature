package com.marketboro.repository;

import com.marketboro.domain.Point;
import com.marketboro.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    Page<Point> findByUserOrderByIdDesc(User user, Pageable pageable);

    List<Point> findByUserOrderByIdDesc(User user);

    Page<Point> findByUser_UserNoOrderByIdDesc(Long userNo, Pageable pageable);
}