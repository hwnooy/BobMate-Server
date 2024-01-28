package com.umc.bobmate.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.bobmate.member.dto.request.PreferenceUploadRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;

import static com.umc.bobmate.ApiDocumentUtils.getDocumentRequest;
import static com.umc.bobmate.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    ObjectMapper objectMapper = new ObjectMapper();

    String accessToken = "";

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("선호도 조회 Test")
    void getPreference() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/members/preference")
                                .header("Authorization", accessToken)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("get-preferences",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Basic auth credentials")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(STRING).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메세지"),
                                fieldWithPath("result").type(OBJECT).description("결과 데이터"),
                                fieldWithPath("result.memberName").type(STRING).description("로그인한 멤버 이름"),
                                fieldWithPath("result.preferenceList").type(ARRAY).description("선호도 리스트")
                        )
                ));
    }

    @Test
    @Transactional
    @DisplayName("선호도 조회 Test")
    void modifyPreference() throws Exception {
        final PreferenceUploadRequest preferenceUploadRequest = new PreferenceUploadRequest();
        final ArrayList<String> list = new ArrayList<>();
        list.add("수정된 선호도 1");
        list.add("수정된 선호도 2");
        preferenceUploadRequest.setPreferenceList(list);

        String content = objectMapper.writeValueAsString(preferenceUploadRequest);

        this.mockMvc.perform(
                        post("/api/v1/members/preference")
                                .header("Authorization", accessToken)
                                .contentType(APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isOk())
                .andDo(document("modify-preferences",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Basic auth credentials")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(STRING).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메세지")
                        )
                ));
    }
}