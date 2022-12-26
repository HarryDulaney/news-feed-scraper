package com.sessionapi.newsscraper.configurations;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CrawlProperties.class)
public class CrawlConfig {}

