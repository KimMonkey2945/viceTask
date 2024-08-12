package com.publicAPI.task;

import com.publicAPI.task.controller.ApiController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApiController.class)
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("openAPI 갤러리리스트 테스트")
    public void callApi() throws Exception {
        this.mvc.perform(get("/api/galleryList"))
                .andExpect(status().isOk())
                .andDo(print());
    }



}
