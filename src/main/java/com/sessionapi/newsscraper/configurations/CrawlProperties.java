package com.sessionapi.newsscraper.configurations;

import com.sessionapi.newsscraper.models.CrawlSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "crawl")
public class CrawlProperties {
    private CrawlSource[] sources;
    private String cronExpression;

    public CrawlSource[] getSources() {
        return sources;
    }

    public void setSources(CrawlSource[] sources) {
        this.sources = sources;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
