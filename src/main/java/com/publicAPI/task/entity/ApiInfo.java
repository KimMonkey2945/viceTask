package com.publicAPI.task.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApiInfo {
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
*/
    @Id
    private Long galContentId;
    private Long galContentTypeId;
    private String galTitle;
    private String galWebImageUrl;

    private LocalDateTime galCreatedtime;
    private LocalDateTime galModifiedtime;
    private String galPhotographyMonth;

    private String galPhotographyLocation;
    private String galPhotographer;
    private String galSearchKeyword;

}
