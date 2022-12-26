package com.sessionapi.newsscraper.events;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class CrawlErrorEvent extends ApplicationEvent {
    private Exception exception;

    public CrawlErrorEvent(Object source) {
        super(source);
    }

    public CrawlErrorEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public CrawlErrorEvent(Object source, Clock clock,Exception exception) {
        super(source, clock);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
