package com.sessionapi.newsscraper.utils;

import com.sessionapi.newsscraper.configurations.CrawlProperties;
import com.sessionapi.newsscraper.entities.Article;
import com.sessionapi.newsscraper.models.CrawlSource;
import org.openqa.selenium.WebElement;

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
                Objects.isNull(article.getOriginUrl()) || Objects.isNull(article.getPublishDate())) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;

    }

    public static boolean isValidArticleLink(WebElement element, CrawlSource crawlSource) {
        String href = element.getDomAttribute("href");
        for (String validFormat : crawlSource.getValidLinkFormats()) {
            if (href.startsWith(validFormat)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
