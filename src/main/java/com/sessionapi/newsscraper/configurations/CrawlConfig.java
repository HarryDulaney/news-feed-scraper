package com.sessionapi.newsscraper.configurations;

import com.sessionapi.newsscraper.events.CrawlEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(CrawlProperties.class)
public class CrawlConfig implements SchedulingConfigurer {
    private final CrawlEventPublisher eventPublisher;
    private final CrawlProperties crawlProperties;

    public CrawlConfig(CrawlEventPublisher eventPublisher,
                       CrawlProperties crawlProperties) {
        this.eventPublisher = eventPublisher;
        this.crawlProperties = crawlProperties;
    }

    void runCrawl() {
        eventPublisher.publishStartCrawl("Crawl requested by scheduler, cron expression is: . " + crawlProperties.getCronExpression());
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
        threadPoolTaskScheduler.initialize();
        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
        threadPoolTaskScheduler.execute(this::runCrawl);
        taskRegistrar.scheduleCronTask(cronTask());
    }


    private CronTask cronTask() {
        return new CronTask(this::runCrawl, crawlProperties.getCronExpression());
    }

}

