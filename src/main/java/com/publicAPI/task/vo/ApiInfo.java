package com.publicAPI.task.vo;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table
public class ApiInfo {
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
*/
    @Id
    private int galContentId;
    private int galContentTypeId;
    private String galTitle;
    private String galWebImageUrl;

    private LocalDateTime galCreatedtime;
    private LocalDateTime galModifiedtime;
    private String galPhotographyMonth;

    private String galPhotographyLocation;
    private String galPhotographer;
    private String galSearchKeyword;
    public ApiInfo() {
    }
}
