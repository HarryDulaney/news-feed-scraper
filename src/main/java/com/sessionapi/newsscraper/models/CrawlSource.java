package com.sessionapi.newsscraper.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class CrawlSource implements Comparable<CrawlSource> {
    private String name;
    private Integer targetArticleCount;
    private String startPointXPath;
    private String articleElement;
    private String originXPath;
    private String titleXPath;
    private boolean hasContextJson;
    private String keywordsSelector;
    private String authorXPath;
    private String dateXPath;
    private String imageXPath;
    private String[] validLinkFormats;
    private String url;

    @Override
    public int compareTo(CrawlSource o) {
        return Character.compare(this.getName().charAt(0), o.getName().charAt(0));

    }

}