package com.sessionapi.newsscraper.events;

import com.sessionapi.newsscraper.services.ArticleService;
import com.sessionapi.newsscraper.services.CrawlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
public class AppEventsListener {
    private final ArticleService articleService;
    private final CrawlService crawlService;

    public AppEventsListener(ArticleService articleService,
                             CrawlService crawlService) {
        this.articleService = articleService;
        this.crawlService = crawlService;
    }

    @EventListener(classes = StartCrawlEvent.class)
    public void onApplicationReady(StartCrawlEvent event) {
        long timestampMillis = event.getTimestamp();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.systemDefault());
        log.info("Starting Crawl Service. TimeStamp: " + localDateTime);
        try {
            crawlService.startCrawl();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("CrawlSource MalformedException. Moving on to the next Crawl Source ");
            crawlService.crawlNext();
        }
    }

    @EventListener(classes = CrawlNextEvent.class)
    public void onCrawlNextEvent(CrawlNextEvent event) {
        long timestampMillis = event.getTimestamp();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.systemDefault());
        log.info("Crawl next seed url... TimeStamp: " + localDateTime);
        crawlService.crawlNext();
    }


    @EventListener(classes = CrawlSuccessEvent.class)
    public void onCrawlSuccessEvent(CrawlSuccessEvent event) {
        articleService.save(event.getArticle());
    }

    @EventListener(classes = CrawlEndEvent.class)
    public void onCrawlEndEvent(CrawlEndEvent event) {
        long timestampMillis = event.getTimestamp();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.systemDefault());
        log.info("Completed crawling all seed urls at: " + localDateTime);
    }

}
