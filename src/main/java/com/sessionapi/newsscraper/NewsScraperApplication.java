package com.sessionapi.newsscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsScraperApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(NewsScraperApplication.class, args)));
    }

}
