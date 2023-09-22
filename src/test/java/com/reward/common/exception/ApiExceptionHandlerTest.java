package com.reward.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reward.controller.PointController;
import com.reward.dto.PointRequest;
import com.reward.service.PointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PointController.class)
class ApiExceptionHandlerTest {
    @MockBean
    PointService pointService;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void handleCustomException() throws Exception {
        //given
        Long userNo = 100L;
        Long usageValue = 1000L;
        String requestBody = objectMapper.writeValueAsString(new PointRequest.PointUsage(userNo, usageValue));

        when(pointService.usePoint(any(PointRequest.PointUsage.class)))
                .thenThrow(new CustomException(ErrorCode.NOT_ENOUGH_POINT));
        //when
        //then
        mockMvc.perform(post("/point/usePoint")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(602))
                .andExpect(jsonPath("$.name").value("NOT_ENOUGH_POINT"))
                .andExpect(jsonPath("$.message").value("사용하시려는 포인트가 소유한 포인트보다 많습니다."))
                .andDo(print());
    }
}