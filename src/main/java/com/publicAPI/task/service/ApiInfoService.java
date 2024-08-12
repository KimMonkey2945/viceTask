package com.publicAPI.task.service;

import com.publicAPI.task.repository.ApiInfoRepository;
import com.publicAPI.task.vo.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ApiInfoService {

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    @Transactional
    public void saveApiInfo(String data) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(data);

            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArray = (JSONArray) items.get("item");

            for (Object obj : itemArray) {
                JSONObject item = (JSONObject) obj;
                ApiInfo apiInfo = new ApiInfo();

                apiInfo.setGalContentId(Integer.parseInt((String) item.get("galContentId")));
                apiInfo.setGalContentTypeId(Integer.parseInt((String) item.get("galContentTypeId")));
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

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    // String을 LocalDateTime으로 변환
    private LocalDateTime convertToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }




}
