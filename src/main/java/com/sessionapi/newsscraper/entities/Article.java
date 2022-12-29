package com.sessionapi.newsscraper.entities;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
@TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrape_origin_url")
    private String scrapeOriginUrl;
    @Column(name = "origin_url")
    private String originUrl;

    @Column(name = "author_full_name")
    private String authorFullName;

    @Column(name = "title")
    private String title;

    @Column(name = "publish_date")
    private String publishDate;

    @Column(name = "img_source")
    private String imageSource;

    @Column(name = "scanned_timestamp")
    private Timestamp scannedTimestamp;

    @Type(type = "string-array")
    @Column(
            name = "categories",
            columnDefinition = "text[]"
    )
    private String[] categories;

    @Column(name = "html_content")
    private String htmlContent;


}
