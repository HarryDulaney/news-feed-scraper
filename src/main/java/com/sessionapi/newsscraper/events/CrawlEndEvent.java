package com.sessionapi.newsscraper.events;

import com.sessionapi.newsscraper.entities.Article;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class CrawlEndEvent extends ApplicationEvent {
    public CrawlEndEvent(Object source) {
        super(source);
    }

    public CrawlEndEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
