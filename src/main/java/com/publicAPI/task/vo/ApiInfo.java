package com.publicAPI.task.vo;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table
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
