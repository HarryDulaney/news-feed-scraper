package com.sessionapi.newsscraper.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto {
    private Integer id;
    private String originUrl;
    private String authorFullName;
    private Date publishDate;
    private String imageSource;
    private Timestamp scannedTimestamp;
    private String sourcePublisher;
    private String[] categories;
    private String htmlContent;

}
