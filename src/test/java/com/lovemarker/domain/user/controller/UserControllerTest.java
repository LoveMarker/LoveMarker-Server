package com.lovemarker.domain.user.controller;

import static javax.management.openmbean.SimpleType.BOOLEAN;
import static javax.management.openmbean.SimpleType.STRING;
import static javax.swing.text.html.parser.DTDConstants.NUMBER;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import com.lovemarker.base.BaseControllerTest;
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

}