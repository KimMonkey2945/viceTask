package com.publicAPI.task.service;

import com.publicAPI.task.repository.ApiInfoRepository;
import com.publicAPI.task.vo.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ApiInfoService {

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    @Transactional
    public void saveApiInfo(InputStream stream) {

        try {
            String data = readStreamToString(stream);
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
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public Page<ApiInfo> getPagedGallaryList(Pageable pageable){
        return apiInfoRepository.findAll(pageable);
    }

    private String readStreamToString(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
            String readLine;
            while ((readLine = br.readLine()) != null) {
                result.append(readLine).append("\n");
            }
        }
        return result.toString();
    }

    // String을 LocalDateTime으로 변환
    private LocalDateTime convertToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }






}
