package com.marketboro.service;

import com.marketboro.common.exception.CustomException;
import com.marketboro.common.exception.ErrorCode;
import com.marketboro.domain.User;
import com.marketboro.dto.UserResponse;
import com.marketboro.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly=true)
    public UserResponse.UserPointAmount getUserPoint(Long userNo) {
        return new UserResponse.UserPointAmount(getUser(userNo));
    }

    public User getUser(Long userNo) {
        return userRepository.findById(userNo)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}