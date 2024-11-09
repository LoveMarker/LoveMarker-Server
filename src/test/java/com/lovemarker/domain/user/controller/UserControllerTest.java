package com.lovemarker.domain.user.controller;

import static javax.management.openmbean.SimpleType.BOOLEAN;
import static javax.management.openmbean.SimpleType.STRING;
import static javax.swing.text.html.parser.DTDConstants.NUMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import com.lovemarker.base.BaseControllerTest;
import com.lovemarker.domain.user.dto.response.FindMyPageResponse;
import com.lovemarker.domain.user.dto.request.UpdateUserNicknameRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class UserControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("성공: 유저 닉네임 변경 api 호출 시")
    void updateUserNickname() throws Exception {
        //given
        Long userId = 1L;
        UpdateUserNicknameRequest request = new UpdateUserNicknameRequest("new_nickname");

        //when
        ResultActions resultActions = mockMvc.perform(patch("/api/user/nickname")
            .header("userId", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions
            .andDo(restDocs.document(
                requestHeaders(
                    headerWithName("userId").description("유저 아이디")
                ),
                requestFields(
                    fieldWithPath("nickname").type(STRING).description("닉네임")
                ),
                responseFields(
                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
                    fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                    fieldWithPath("message").type(STRING).description("메시지"),
                    fieldWithPath("data").ignored()
                )
            ));
    }

    @Test
    @DisplayName("성공: 마이페이지 조회 api 호출 시")
    void findMyPage() throws Exception {
        //given
        Long userId = 1L;
        FindMyPageResponse response = new FindMyPageResponse(true, "닉네임", null, null);

        given(userService.findMyPage(any())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/user")
            .header("userId", userId)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
            .andDo(restDocs.document(
                requestHeaders(
                    headerWithName("userId").description("유저 아이디")
                ),
                responseFields(
                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
                    fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                    fieldWithPath("message").type(STRING).description("메시지"),
                    fieldWithPath("data.isMatched").type(BOOLEAN).description("매칭 여부"),
                    fieldWithPath("data.nickname").type(STRING).description("유저 닉네임"),
                    fieldWithPath("data.anniversaryDays").type(STRING).optional().description("기념일 디데이"),
                    fieldWithPath("data.partnerNickname").type(STRING).optional().description("상대방 닉네임")
                )
            ));
    }
}
