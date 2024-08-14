package com.publicAPI.task.service;

import com.publicAPI.task.repository.ApiInfoRepository;
import com.publicAPI.task.vo.ApiInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ApiInfoServiceImplTest {

    // 실제 db에 접근하는 역할이지만, 가짜객체를 사용해서 외부 의존성 제거
    @Mock
    private ApiInfoRepository apiInfoRepository;
    // 인스턴스를 생성하고, @Mock으로 선언된 목 객체(apiInfoRepository)를 이 인스턴스에 주입합니다.
    @InjectMocks
    private ApiInfoServiceImpl apiInfoServiceImpl;

    // @Mock, @InjectMocks가 붙은 필드 초기화
    // @Mock 어노테이션이 붙은 필드들은 Mockito가 가짜 객체(Mock Object)로 자동 생성하고, @InjectMocks가 붙은 필드에는 해당 가짜 객체들이 주입
    public ApiInfoServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    // saveApiInfo가 JSON파싱과 엔티티 저장 로직이 정상적으로 작동하는지를 검증.
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

    @Test
    @DisplayName("openAPI searchByKeyword test")
    public void testSearchByKeyword(){
        String keyword = "우륵기념탑";
        List<ApiInfo> result = apiInfoServiceImpl.searchByKeyword(keyword);
        Assertions.assertFalse(result.isEmpty(), "not be empty");

//        boolean contatin = result.stream().anyMatch(apiInfo -> apiInfo.getGalSearchKeyword().equals(keyword));
//        Assertions.assertTrue(contatin, "contatin keyword");


    }



}
