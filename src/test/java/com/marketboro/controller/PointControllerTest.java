package com.marketboro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketboro.domain.Point;
import com.marketboro.domain.User;
import com.marketboro.domain.enums.PointStatus;
import com.marketboro.dto.PointRequest;
import com.marketboro.dto.PointResponse;
import com.marketboro.dto.UserResponse;
import com.marketboro.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PointController.class)
class PointControllerTest {

    @MockBean
    PointService pointService;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    Long userNo = 100L;
    User user = User.builder()
            .userNo(userNo)
            .userName("marketboro")
            .pointAmount(5000L)
            .build();

    @DisplayName("유저 포인트 적립/사용 내역 조회")
    @Test
    void getPointHistory() throws Exception {
        //given
        int page = 0;
        int size = 5;
        String requestBody = objectMapper.writeValueAsString(new PointRequest.PointHistory(userNo, page, size));
        List<PointResponse.PointHistory> pointHistoryList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Point point = Point.builder()
                    .pointNo((long) i)
                    .pointValue((long) (i * 100))
                    .pointStatus(PointStatus.REWARD)
                    .user(user)
                    .build();
            PointResponse.PointHistory pointHistory = new PointResponse.PointHistory(point);
            pointHistoryList.add(pointHistory);
        }

        when(pointService.getPointHistory(any(PointRequest.PointHistory.class)))
                .thenReturn(pointHistoryList);

        //when
        //then
        mockMvc.perform(post("/point/getPointHistory")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(size)))
                .andDo(print());
    }

    @DisplayName("유저 포인트 적립")
    @Test
    void rewardPoint() throws Exception {
        //given
        Long rewardValue = 1000L;
        String requestBody = objectMapper.writeValueAsString(new PointRequest.PointReward(userNo, rewardValue));
        UserResponse.UserPointAmount userPointAmount = new UserResponse.UserPointAmount(user);

        when(pointService.rewardPoint(any(PointRequest.PointReward.class)))
                .thenReturn(userPointAmount);

        //when
        //then
        mockMvc.perform(post("/point/rewardPoint")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userNo").value(user.getUserNo()))
                .andExpect(jsonPath("$.userName").value(user.getUserName()))
                .andExpect(jsonPath("$.pointAmount").value(user.getPointAmount()))
                .andDo(print());
    }

    @DisplayName("유저 포인트 사용")
    @Test
    void usePoint() throws Exception {
        //given
        Long usageValue = 1000L;
        String requestBody = objectMapper.writeValueAsString(new PointRequest.PointUsage(userNo, usageValue));
        UserResponse.UserPointAmount userPointAmount = new UserResponse.UserPointAmount(user);

        when(pointService.usePoint(any(PointRequest.PointUsage.class)))
                .thenReturn(userPointAmount);

        //when
        //then
        mockMvc.perform(post("/point/usePoint")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userNo").value(user.getUserNo()))
                .andExpect(jsonPath("$.userName").value(user.getUserName()))
                .andExpect(jsonPath("$.pointAmount").value(user.getPointAmount()))
                .andDo(print());

    }
}