package com.lovemarker.domain.auth.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.lovemarker.base.BaseControllerTest;
import com.lovemarker.domain.auth.dto.request.SignInRequest;
import com.lovemarker.domain.auth.dto.response.ReissueTokenResponse;
import com.lovemarker.domain.auth.dto.response.SignInResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class AuthControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("성공: 로그인 api 호출 시")
    void signIn() throws Exception {
        //given
        SignInRequest request = new SignInRequest("test_token", "GOOGLE");
        SignInResponse response = new SignInResponse("Login", "access_token", "refresh_token");

        given(authService.signIn(any(), any())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions
            .andDo(restDocs.document(
                requestFields(
                    fieldWithPath("token").type(STRING).description("소셜 토큰"),
                    fieldWithPath("provider").type(STRING).description("소셜 타입")
                ),
                responseFields(
                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
                    fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                    fieldWithPath("message").type(STRING).description("메시지"),
                    fieldWithPath("data.type").type(STRING).description("로그인/회원가입"),
                    fieldWithPath("data.accessToken").type(STRING).description("액세스 토큰"),
                    fieldWithPath("data.refreshToken").type(STRING).description("리프레시 토큰")
                )
            ));
    }

    @Test
    @DisplayName("성공: 토큰 재발급 api 호출 시")
    void reissueToken() throws Exception {
        //given
        ReissueTokenResponse response = new ReissueTokenResponse("new token");

        given(authService.reissueToken(any())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/auth/reissue-token")
            .header("refreshToken", "token"));

        //then
        resultActions
            .andDo(restDocs.document(
                requestHeaders(
                    headerWithName("refreshToken").description("리프레시 토큰")
                ),
                responseFields(
                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
                    fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                    fieldWithPath("message").type(STRING).description("메시지"),
                    fieldWithPath("data.accessToken").type(STRING).description("액세스 토큰")
                )
            ));
    }
}
