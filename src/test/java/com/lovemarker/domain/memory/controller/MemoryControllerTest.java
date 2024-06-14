package com.lovemarker.domain.memory.controller;

import static javax.management.openmbean.SimpleType.BOOLEAN;
import static javax.management.openmbean.SimpleType.STRING;
import static javax.swing.text.html.parser.DTDConstants.NUMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.lovemarker.base.BaseControllerTest;
import com.lovemarker.domain.memory.dto.response.FindMemoryDetail;
import com.lovemarker.domain.memory.dto.response.FindMemoryListResponse;
import com.lovemarker.domain.memory.dto.response.FindMemoryListResponse.FindMemoryResponse;
import com.lovemarker.global.dto.PageInfo;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

class MemoryControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("성공: 추억 상세 조회 api 호출 시")
    void findMemoryDetail() throws Exception {
        //given
        Long userId = 1L;
        Long memoryId = 1L;
        FindMemoryDetail response = new FindMemoryDetail(memoryId, "title", "content",
            LocalDate.now(), "address", "별명", true, List.of("url1", "url2"));

        given(memoryService.findMemoryDetail(any(), any())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/memory/{memoryId}", memoryId)
            .header("userId", userId)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
            .andDo(restDocs.document(
                requestHeaders(
                    headerWithName("userId").description("유저 아이디")
                ),
                pathParameters(
                    parameterWithName("memoryId").description("추억 아이디")
                ),
                responseFields(
                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
                    fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                    fieldWithPath("message").type(STRING).description("메시지"),
                    fieldWithPath("data.memoryId").type(NUMBER).description("추억 아이디"),
                    fieldWithPath("data.title").type(STRING).description("제목"),
                    fieldWithPath("data.content").type(STRING).description("내용"),
                    fieldWithPath("data.date").type(STRING).description("날짜"),
                    fieldWithPath("data.address").type(STRING).description("주소"),
                    fieldWithPath("data.writer").type(STRING).description("작성자 닉네임"),
                    fieldWithPath("data.isWriter").type(BOOLEAN).description("작성 여부"),
                    fieldWithPath("data.images[]").type(ARRAY).description("이미지")
                )
            ));
    }

    @Test
    @DisplayName("성공: 추억 리스트뷰 조회 api 호출 시")
    void findMemoryList() throws Exception {
        //given
        Long userId = 1L;
        FindMemoryResponse findMemoryResponse = new FindMemoryResponse(1L, "title",
            LocalDate.now(), "address", "url");
        PageInfo pageInfo = new PageInfo(1, false);
        FindMemoryListResponse response = new FindMemoryListResponse(pageInfo, List.of(findMemoryResponse));

        given(memoryService.findMemoryList(any(), anyInt(), anyInt())).willReturn(response);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "10");

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/memory/list-view")
            .header("userId", userId)
            .params(params));

        //then
        resultActions.andDo(
            restDocs.document(
                requestHeaders(
                    headerWithName("userId").description("유저 아이디")
                ),
                queryParameters(
                    parameterWithName("page").description("page"),
                    parameterWithName("size").description("size")
                ),
                responseFields(
                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
                    fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                    fieldWithPath("message").type(STRING).description("메시지"),
                    fieldWithPath("data.pageInfo.totalElements").type(NUMBER).description("총 데이터 수"),
                    fieldWithPath("data.pageInfo.hasNext").type(BOOLEAN).description("다음 페이지 존재 여부"),
                    fieldWithPath("data.memories[].memoryId").type(NUMBER).description("추억 ID"),
                    fieldWithPath("data.memories[].title").type(STRING).description("제목"),
                    fieldWithPath("data.memories[].date").type(STRING).description("날짜"),
                    fieldWithPath("data.memories[].address").type(STRING).description("주소"),
                    fieldWithPath("data.memories[].image").type(STRING).description("대표 이미지")
                )
            ));
    }

    @Test
    @DisplayName("성공: 내가 올린 추억 리스트 조회 api 호출 시")
    void findMyMemoryList() throws Exception {
        //given
        Long userId = 1L;
        FindMemoryResponse findMemoryResponse = new FindMemoryResponse(1L, "title",
            LocalDate.now(), "address", "url");
        PageInfo pageInfo = new PageInfo(1, false);
        FindMemoryListResponse response = new FindMemoryListResponse(pageInfo, List.of(findMemoryResponse));

        given(memoryService.findMyMemoryList(any(), anyInt(), anyInt())).willReturn(response);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "10");

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/memory/me")
            .header("userId", userId)
            .params(params));

        //then
        resultActions.andDo(
            restDocs.document(
                requestHeaders(
                    headerWithName("userId").description("유저 아이디")
                ),
                queryParameters(
                    parameterWithName("page").description("page"),
                    parameterWithName("size").description("size")
                ),
                responseFields(
                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
                    fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                    fieldWithPath("message").type(STRING).description("메시지"),
                    fieldWithPath("data.pageInfo.totalElements").type(NUMBER).description("총 데이터 수"),
                    fieldWithPath("data.pageInfo.hasNext").type(BOOLEAN).description("다음 페이지 존재 여부"),
                    fieldWithPath("data.memories[].memoryId").type(NUMBER).description("추억 ID"),
                    fieldWithPath("data.memories[].title").type(STRING).description("제목"),
                    fieldWithPath("data.memories[].date").type(STRING).description("날짜"),
                    fieldWithPath("data.memories[].address").type(STRING).description("주소"),
                    fieldWithPath("data.memories[].image").type(STRING).description("대표 이미지")
                )
            ));
    }
}
