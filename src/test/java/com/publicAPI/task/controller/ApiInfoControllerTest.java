package com.publicAPI.task.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test testCallApiInfo")
    public void testCallApiInfo() throws Exception {
        mockMvc.perform(get("/api/getapiinfo")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print());


    }

    @Test
    @DisplayName("Test saveApiInfo")
    public void testSaveApiInfo() throws Exception {

        String json = "{\"response\": {\"body\": {\"items\": {\"item\": [{\"galContentId\": \"1\"}]}}}}";

        mockMvc.perform(post("/api/saveapiinfo")
                        .contentType("application/json")
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("save success"))
                .andDo(print());
    }

}
