package com.publicAPI.task.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// 애플리케이션이 구동될 때와 동일한 컨텍스트 생성, 실제 db나 외부시스템과 통합테스트 진행
@SpringBootTest
// MockMvc의 의존성을 자동으로 주입, REST API 테스트를 할 때 사용.
@AutoConfigureMockMvc
public class ApiInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // api를 잘 불러오는지 검증.
    @Test
    @DisplayName("Test testCallApiInfo")
    public void testCallApiInfo() throws Exception {
        mockMvc.perform(get("/api/getapiinfo")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print());


    }

    // api를 불러와 잘 저장하는지 검증.
    @Test
    @DisplayName("Test saveApiInfo")
//    @Transactional //실제 데이터가 저장되는지 확인을 위해 주석 처리하고 진행.
    public void testSaveApiInfo() throws Exception {

        String json = "{\"response\": {\"body\": {\"items\": {\"item\": [{\"galContentId\": \"1\"}]}}}}";

        mockMvc.perform(post("/api/saveapiinfo")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("save success"))
                .andDo(print());
    }

}
