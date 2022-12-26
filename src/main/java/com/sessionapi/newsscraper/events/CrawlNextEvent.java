package com.sessionapi.newsscraper.events;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class CrawlNextEvent extends ApplicationEvent {
    public CrawlNextEvent(Object source) {
        super(source);
    }

    public CrawlNextEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
