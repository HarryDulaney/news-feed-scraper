package com.sessionapi.newsscraper.utils;

import com.sessionapi.newsscraper.entities.Article;
import com.sessionapi.newsscraper.models.CrawlSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ScrapeUtility {
    public static Timestamp timeStampNow() {
        return Timestamp.from(Instant.now());
    }

    public static String[] getKeywordsFromContext(Article article, CrawlSource source) {
        Set<String> keywords = new HashSet<>();
        String htmlContent = article.getHtmlContent();
        int index = htmlContent.indexOf("keywords");
        if (index == -1) {
            log.info("---------------------------- No keywords found in @Context ----------------------------");
            return new String[]{""};
        }
        String temp = htmlContent.substring(index);
        int start = temp.indexOf("[") + 1;
        int end = temp.indexOf("]");
        String keywordsString = temp.substring(start, end);
        return keywordsString.split(",");
    }

}
