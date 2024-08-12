package com.publicAPI.task.repository;

import com.publicAPI.task.vo.ApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiInfoRepository extends JpaRepository<ApiInfo, Integer> {

}
