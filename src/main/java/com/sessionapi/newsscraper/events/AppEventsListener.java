package com.sessionapi.newsscraper.events;

import com.sessionapi.newsscraper.services.ArticleService;
import com.sessionapi.newsscraper.services.CrawlService;
import com.sessionapi.newsscraper.services.ScrapeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
public class AppEventsListener {
    private ConfigurableApplicationContext applicationContext;
    private final ArticleService articleService;
    private final CrawlService crawlService;
    private ScrapeService scrapeService;

    public AppEventsListener(ArticleService articleService,
                             CrawlService crawlService,
                             ScrapeService scrapeService) {
        this.articleService = articleService;
        this.crawlService = crawlService;
        this.scrapeService = scrapeService;
    }

    @EventListener(classes = ApplicationReadyEvent.class)
    public void onReady(ApplicationReadyEvent event) {
        this.applicationContext = event.getApplicationContext();
    }

    @EventListener(classes = StartCrawlEvent.class)
    public void onApplicationReady(StartCrawlEvent event) {
        long timestampMillis = event.getTimestamp();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.systemDefault());
        log.info("Starting Crawl Service. TimeStamp: " + localDateTime);
        try {
            crawlService.startCrawl();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception. Moving on to the next Crawl Source ");
            try {
                crawlService.crawlNext();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @EventListener(classes = CrawlNextEvent.class)
    public void onCrawlNextEvent(CrawlNextEvent event) {
        long timestampMillis = event.getTimestamp();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.systemDefault());
        log.info("Crawl next seed url... TimeStamp: " + localDateTime);
        try {
            crawlService.crawlNext();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @EventListener(classes = CrawlSuccessEvent.class)
    public void onCrawlSuccessEvent(CrawlSuccessEvent event) {
        log.info("Crawl Success...");
        articleService.save(event.getArticle());
    }

    @EventListener(classes = CrawlErrorEvent.class)
    public void onErrorEvent(CrawlErrorEvent event) {
        log.info("CrawlErrorEvent occurred: ");
        if (event.getException() != null) {
            log.info("Crawl Error Event with Exception: " + event.getException().getMessage());
            log.info("Exception cause: " + event.getException().getCause());
        }
        log.info("Error message: " + event.getMessage());
        log.info("Error TimeStamp: " + event.getTimestamp());
    }

    @EventListener(classes = CrawlEndEvent.class)
    public void onCrawlEndEvent(CrawlEndEvent event) {
        log.info("Crawl Complete...");
        long timestampMillis = event.getTimestamp();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.systemDefault());
        log.info("Completed crawling all seed urls at: " + localDateTime);

    }

}
