package com.lovemarker.base;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovemarker.base.config.RestDocsConfig;
import com.lovemarker.domain.auth.service.AuthService;
import com.lovemarker.domain.couple.service.CoupleService;
import com.lovemarker.global.config.resolver.UserIdResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest
@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public abstract class BaseControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @MockBean
    protected CoupleService coupleService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected UserIdResolver userIdResolver;

    @BeforeEach
    void setUp(
        WebApplicationContext applicationContext,
        RestDocumentationContextProvider documentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
            .alwaysDo(print())
            .alwaysDo(restDocs)
            .apply(
                MockMvcRestDocumentation.documentationConfiguration(documentationContextProvider))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }
}
