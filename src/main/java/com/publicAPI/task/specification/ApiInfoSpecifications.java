package com.publicAPI.task.specification;

import com.publicAPI.task.entity.ApiInfo;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class ApiInfoSpecifications {

    public static Specification<ApiInfo> searchKeyword(String keyword){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("galSearchKeyword"), "%" + keyword + "%");
    }

    public static Specification<ApiInfo> searchMonthAndLocation(String photographyMonth, String photographyLocation){
        return (root, query, criteriaBuilder) ->{
            Predicate monthPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("galPhotographyMonth"), photographyMonth);
            Predicate locationPredicate = criteriaBuilder.like(root.get("galPhotographyLocation"), photographyLocation);
            return criteriaBuilder.and(monthPredicate, locationPredicate);
        };

    }

}
