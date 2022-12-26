package com.sessionapi.newsscraper.services;

import com.sessionapi.newsscraper.events.CrawlEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("ScheduleService")
public class ScheduleService {
    private final CrawlEventPublisher eventPublisher;

    public ScheduleService(CrawlEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    @Scheduled(fixedDelay = 86400)
    public void schedule() {
        eventPublisher.publishStartCrawl("Daily crawl requested by scheduler.");
    }
}
