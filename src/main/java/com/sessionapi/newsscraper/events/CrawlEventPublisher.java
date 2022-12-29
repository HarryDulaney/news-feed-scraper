package com.sessionapi.newsscraper.events;

import com.sessionapi.newsscraper.entities.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Slf4j
@Component
public class CrawlEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public CrawlEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishStartCrawl(final String message) {
        log.debug("Publishing start crawl event.");
        StartCrawlEvent event = new StartCrawlEvent(this, Clock.systemDefaultZone());
        applicationEventPublisher.publishEvent(event);
    }

    public void publishCrawlNext(final String message) {
        log.debug("Publishing start scrape event...");
        CrawlNextEvent event = new CrawlNextEvent(this, Clock.systemDefaultZone());
        applicationEventPublisher.publishEvent(event);
    }

    public void publishCrawlSuccess(final Article article) {
        log.debug("Publishing crawl seed success event...");
        CrawlSuccessEvent event = new CrawlSuccessEvent(this, Clock.systemDefaultZone(), article);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishCrawlEnding(final String message) {
        log.debug("Publishing crawl end event. With message: " + message);
        CrawlEndEvent event = new CrawlEndEvent(this, Clock.systemDefaultZone());
        applicationEventPublisher.publishEvent(event);
    }

    public void publishErrorEvent(final String message, Exception exception) {
        log.debug("Publishing a CrawlErrorEvent. With message: " + message);
        CrawlErrorEvent event = new CrawlErrorEvent(this, Clock.systemDefaultZone(), exception);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishErrorEvent(final String message) {
        log.debug("Publishing CrawlErrorEvent...");
        CrawlErrorEvent event = new CrawlErrorEvent(this, Clock.systemDefaultZone(), message);
        applicationEventPublisher.publishEvent(event);
    }
}