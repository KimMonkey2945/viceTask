package com.publicAPI.task.specification;

import com.publicAPI.task.vo.ApiInfo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ApiInfoSpecifications {

    public static Specification<ApiInfo> keyword(String keyword){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("galSearchKeyword"), "%" + keyword + "%");
    }


}
