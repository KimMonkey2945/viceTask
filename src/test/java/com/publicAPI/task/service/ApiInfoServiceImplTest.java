package com.publicAPI.task.service;

import com.publicAPI.task.repository.ApiInfoRepository;
import com.publicAPI.task.vo.ApiInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
public class ApiInfoServiceImplTest {

    @Mock
    private ApiInfoRepository apiInfoRepository;

    @InjectMocks
    private ApiInfoServiceImpl apiInfoServiceImpl;

    public ApiInfoServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("openAPI saveApiInfo test")
    @Transactional
    public void testSaveApiInfo() throws Exception {
        String json = "{"
                + "  \"response\": {"
                + "    \"body\": {"
                + "      \"items\": {"
                + "        \"item\": ["
                + "          {"
                + "            \"galContentId\": \"1\","
                + "            \"galContentTypeId\": \"2\","
                + "            \"galTitle\": \"Title 1\","
                + "            \"galWebImageUrl\": \"http://example.com/image1.jpg\","
                + "            \"galCreatedtime\": \"20230801123000\","
                + "            \"galModifiedtime\": \"20230801123001\","
                + "            \"galPhotographyMonth\": \"202308\","
                + "            \"galPhotographyLocation\": \"Location\","
                + "            \"galPhotographer\": \"Photographer\","
                + "            \"galSearchKeyword\": \"keyword1\""
                + "          }"
                + "        ]"
                + "      }"
                + "    }"
                + "  }"
                + "}";

        InputStream inputStream = new ByteArrayInputStream(json.getBytes("UTF-8"));

        apiInfoServiceImpl.saveApiInfo(inputStream);

        verify(apiInfoRepository, times(1)).save(any(ApiInfo.class));
    }

}
