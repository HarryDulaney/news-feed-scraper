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
    private String articleSelector;
    private String articleSelectorType;
    private String targetSelector;
    private String targetSelectorType;
    private String titleSelector;
    private String titleSelectorType;
    private String authorSelector;
    private String authorSelectorType;
    private String dateSelector;
    private String dateSelectorType;
    private String imageSelector;
    private String imageSelectorType;
    private String[] validLinkFormats;
    private String url;

    @Override
    public int compareTo(CrawlSource o) {
        return Character.compare(this.getName().charAt(0), o.getName().charAt(0));

    }

}