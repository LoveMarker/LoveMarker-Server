package com.lovemarker.domain.couple.controller;

import static javax.management.openmbean.SimpleType.BOOLEAN;
import static javax.management.openmbean.SimpleType.STRING;
import static javax.swing.text.html.parser.DTDConstants.NUMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.lovemarker.base.BaseControllerTest;
import com.lovemarker.domain.couple.dto.request.CreateInvitationCodeRequest;
import com.lovemarker.domain.couple.dto.response.CreateInvitationCodeResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class CoupleControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("성공: 초대 코드 생성 api 호출 시")
    void createInvitationCode() throws Exception {
        //given
        Long userId = 1L;
        CreateInvitationCodeRequest request = new CreateInvitationCodeRequest(LocalDate.parse("2023-02-15"));
        CreateInvitationCodeResponse response = new CreateInvitationCodeResponse("test_code");

        given(coupleService.createInvitationCode(anyLong(), any())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/couple")
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
                    fieldWithPath("anniversary").type(STRING).description("기념일")
                ),
                responseFields(
                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
                    fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                    fieldWithPath("message").type(STRING).description("메시지"),
                    fieldWithPath("data.invitationCode").type(STRING).description("초대 코드")
                )
            ));
    }
}