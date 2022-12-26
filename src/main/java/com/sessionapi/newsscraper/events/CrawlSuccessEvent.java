package com.sessionapi.newsscraper.events;

import com.sessionapi.newsscraper.entities.Article;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class CrawlSuccessEvent extends ApplicationEvent {
    private Article article;

    public CrawlSuccessEvent(Object source) {
        super(source);
    }

    public CrawlSuccessEvent(Object source, Clock clock, Article article) {
        super(source, clock);
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }
}
