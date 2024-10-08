package com.publicAPI.task.service;

import com.publicAPI.task.repository.ApiInfoRepository;
import com.publicAPI.task.specification.ApiInfoSpecifications;
import com.publicAPI.task.entity.ApiInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class ApiInfoServiceImpl implements ApiInfoService {

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    @Transactional
    public void saveApiInfo(InputStream data) {

        JSONObject jsonObject = parseJSONFromStream(data);

        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArray = (JSONArray) items.get("item");

        for (Object obj : itemArray) {
            JSONObject item = (JSONObject) obj;
            ApiInfo apiInfo = new ApiInfo();

            apiInfo.setGalContentId(Long.parseLong((String) item.get("galContentId")));
            apiInfo.setGalContentTypeId(Long.parseLong((String) item.get("galContentTypeId")));
            apiInfo.setGalTitle((String) item.get("galTitle"));
            apiInfo.setGalWebImageUrl((String) item.get("galWebImageUrl"));
            apiInfo.setGalCreatedtime(convertToLocalDateTime((String) item.get("galCreatedtime")));
            apiInfo.setGalModifiedtime(convertToLocalDateTime((String) item.get("galModifiedtime")));
            apiInfo.setGalPhotographyMonth((String)item.get("galPhotographyMonth"));
            apiInfo.setGalPhotographyLocation((String) item.get("galPhotographyLocation"));
            apiInfo.setGalPhotographer((String) item.get("galPhotographer"));
            apiInfo.setGalSearchKeyword((String) item.get("galSearchKeyword"));

            apiInfoRepository.save(apiInfo);
        }

    }

    public List<ApiInfo> searchByKeyword(String keyword){
        Specification<ApiInfo> spec = ApiInfoSpecifications.searchKeyword(keyword);
        return apiInfoRepository.findAll(spec);
    }

    public List<ApiInfo> searchByMonthAndLocation(String month, String location){
        Specification<ApiInfo> spec = ApiInfoSpecifications.searchMonthAndLocation(month, location);
        return apiInfoRepository.findAll(spec);
    }

    // String을 LocalDateTime으로 변환
    private LocalDateTime convertToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    private JSONObject parseJSONFromStream(InputStream stream){
        JSONParser parser = new JSONParser();
        try(InputStreamReader reader = new InputStreamReader(stream, "UTF-8")) {
            return (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            log.error("failed to parse JSON from stream", e);
            return new JSONObject();
        }
    }

}
