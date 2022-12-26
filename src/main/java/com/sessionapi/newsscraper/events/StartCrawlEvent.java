package com.sessionapi.newsscraper.events;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class StartCrawlEvent extends ApplicationEvent {
    public StartCrawlEvent(Object source) {
        super(source);
    }

    public StartCrawlEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
