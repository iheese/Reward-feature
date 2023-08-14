package com.marketboro.repository;

import com.marketboro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 유저 id 로 유저 조회
     *
     * @param userNo must not be {@literal null}.
     * @return 옵셔널 처리된 유저
     */
    Optional<User> findById(Long userNo);
}