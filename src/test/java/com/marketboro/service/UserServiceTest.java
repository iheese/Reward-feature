package com.marketboro.service;

import com.marketboro.domain.User;
import com.marketboro.dto.UserResponse;
import com.marketboro.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @DisplayName("유저 조회")
    @Test
    void getUserPoint() {
        //given
        Long userNo = 100L;
        Optional<User> user = Optional.of(new User());
        UserResponse.UserPointAmount userDto = new UserResponse.UserPointAmount(user.get());
        when(userRepository.findById(userNo)).thenReturn(user);

        //when
        UserResponse.UserPointAmount result = userService.getUserPoint(userNo);

        //then
        assertEquals(userDto.getUserNo(), result.getUserNo());
    }

    @DisplayName("DB 에서 유저 조회")
    @Test
    void getUser() {
        //given
        Long userNo = 100L;
        User user = new User();
        when(userRepository.findById(userNo)).thenReturn(Optional.of(user));

        //when
        User result = userService.getUser(userNo);

        //then
        assertEquals(user, result);
    }
}