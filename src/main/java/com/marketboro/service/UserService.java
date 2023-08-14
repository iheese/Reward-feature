package com.marketboro.service;

import com.marketboro.common.exception.CustomException;
import com.marketboro.common.exception.ErrorCode;
import com.marketboro.domain.User;
import com.marketboro.dto.UserResponse;
import com.marketboro.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 유저 포인트 총액을 포함한 관련 데이터 조회
     *
     * @param userNo 유저 ID
     * @return 유저 데이터
     */
    @Transactional(readOnly=true)
    public UserResponse.UserPointAmount getUserPoint(Long userNo) {
        log.info("[getUserPoint] userNo {} 번 유저 포인트 조회", userNo);
        return new UserResponse.UserPointAmount(getUser(userNo));
    }

    /**
     * 유저 ID로 유저 조회
     *
     * @param userNo 유저 ID
     * @return 유저
     */
    public User getUser(Long userNo) {
        return userRepository.findById(userNo)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}