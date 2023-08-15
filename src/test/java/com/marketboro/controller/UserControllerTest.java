package com.marketboro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketboro.domain.User;
import com.marketboro.dto.UserRequest;
import com.marketboro.dto.UserResponse;
import com.marketboro.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("유저 포인트 총액 조회 컨트롤러")
    @Test
    void getUserPoint() throws Exception {
        //given
        Long userNo = 100L;
        String requestBody = objectMapper
                .writeValueAsString(UserRequest.builder()
                .userNo(userNo)
                .build());
        User user = User.builder()
                .userNo(userNo)
                .userName("marketboro")
                .pointAmount(1000L)
                .build();

        when(userService.getUserPoint(userNo)).thenReturn(new UserResponse.UserPointAmount(user));

        //when
        //then
        mockMvc.perform(post("/user/getUserPoint")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userNo").value(user.getUserNo()))
                .andExpect(jsonPath("$.userName").value(user.getUserName()))
                .andExpect(jsonPath("$.pointAmount").value(user.getPointAmount()))
                .andDo(print());
    }
}