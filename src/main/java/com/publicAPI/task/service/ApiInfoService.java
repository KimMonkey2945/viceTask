package com.publicAPI.task.service;

import com.publicAPI.task.entity.ApiInfo;

import java.io.InputStream;
import java.util.List;

public interface ApiInfoService {

    public void saveApiInfo(InputStream stream);

    public List<ApiInfo> searchByKeyword(String keyword);
}
