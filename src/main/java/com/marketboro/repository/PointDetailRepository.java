package com.marketboro.repository;

import com.marketboro.domain.PointDetail;
import com.marketboro.domain.User;
import com.marketboro.domain.enums.PointDetailStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointDetailRepository extends JpaRepository<PointDetail, Long> {
    List<PointDetail> findByUserAndPointDetailStatusOrderByExpDateAsc(User user, PointDetailStatus pointDetailStatus);
}