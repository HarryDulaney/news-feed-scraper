package com.sessionapi.newsscraper.events;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class CrawlErrorEvent extends ApplicationEvent {
    private Exception exception;
    private String message;

    public CrawlErrorEvent(Object source) {
        super(source);
    }

    public CrawlErrorEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public CrawlErrorEvent(Object source, Clock clock, String message) {
        this(source, clock);
        this.message = message;
        this.exception = null;
    }

    public CrawlErrorEvent(Object source, Clock clock, Exception exception) {
        this(source, clock);
        this.exception = exception;
        this.message = exception.getMessage();
    }

    public CrawlErrorEvent(Object source, Clock clock, String message, Exception exception) {
        this(source, clock, message);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }
}
