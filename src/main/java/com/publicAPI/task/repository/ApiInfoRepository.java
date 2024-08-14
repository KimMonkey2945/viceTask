package com.publicAPI.task.repository;

import com.publicAPI.task.vo.ApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ApiInfoRepository extends JpaRepository<ApiInfo, Integer>, JpaSpecificationExecutor<ApiInfo> {

}
