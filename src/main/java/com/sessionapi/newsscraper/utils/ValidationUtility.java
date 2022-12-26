package com.sessionapi.newsscraper.utils;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.sessionapi.newsscraper.configurations.CrawlProperties;
import com.sessionapi.newsscraper.entities.Article;
import com.sessionapi.newsscraper.models.CrawlSource;
import com.sessionapi.newsscraper.common.Constants;

import java.util.Objects;

public class ValidationUtility {
    public static boolean isValid(CrawlProperties crawlProperties) {
        if (Objects.isNull(crawlProperties.getSources()) || crawlProperties.getSources().length < 1) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static boolean isValid(Article article) {
        if (Objects.isNull(article.getTitle()) || Objects.isNull(article.getHtmlContent()) ||
                Objects.isNull(article.getAuthorFullName()) || Objects.isNull(article.getOriginUrl()) ||
                Objects.isNull(article.getSourcePublisher()) || Objects.isNull(article.getPublishDate())) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;

    }


    public static boolean hasNextCrawl(int nextSeedIndex, CrawlSource[] sources) {
        return (sources.length > nextSeedIndex && sources[nextSeedIndex] != null);
    }

    public static boolean isValidArticleLink(HtmlAnchor anchor, CrawlSource crawlSource) {
        String href = anchor.getHrefAttribute();
        for (String validFormat : crawlSource.getValidLinkFormats()) {
            if (href.startsWith(validFormat)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
