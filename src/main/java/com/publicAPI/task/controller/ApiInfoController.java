package com.publicAPI.task.controller;

import com.publicAPI.task.service.ApiInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiInfoController {

    @Value("${openApi.callBackUrl}")
    private String callBackUrl;
    @Autowired
    ApiInfoServiceImpl apiInfoServiceImpl;

    // openApi를 불러오는 역할
    @GetMapping("/getapiinfo")
    public ResponseEntity<JSONObject> callApiInfo(){
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        JSONObject result = null;

        String urlStr = callBackUrl;

        try{
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            stream = getNetworkConnection(urlConnection);
            result = parseJSONFromStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // openApi를 불러와 저장하는 역할 
    @PostMapping("/saveapiinfo")
    public ResponseEntity<String> saveApiInfo(){
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;

        String urlStr = callBackUrl;

        try{
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            stream = getNetworkConnection(urlConnection);
            apiInfoServiceImpl.saveApiInfo(stream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }

        return new ResponseEntity<>("save success", HttpStatus.OK);

    }



    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
//      실질적으로 연결이 지연되더라도 계속 기다리거나, 데이터 전송중에 네트워크 문제가 방생할 때 무한대기 방지를 위해 설정
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000);

        if(urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }
        return urlConnection.getInputStream();
    }

    public JSONObject parseJSONFromStream(InputStream stream){
        JSONParser parser = new JSONParser();
        try(InputStreamReader reader = new InputStreamReader(stream, "UTF-8")) {
           return (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            log.error("failed to parse JSON from stream", e);
            return new JSONObject();
        }
    }

}
