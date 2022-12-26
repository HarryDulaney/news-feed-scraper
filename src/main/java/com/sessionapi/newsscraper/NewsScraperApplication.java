package com.sessionapi.newsscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NewsScraperApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsScraperApplication.class, args);
    }

}
