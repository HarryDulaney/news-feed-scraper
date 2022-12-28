package com.sessionapi.newsscraper.configurations;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Profile("local")
public class LocalSeleniumConfig {
    @PostConstruct
    void configureLocal() throws IOException {
        Resource resource = new ClassPathResource("chromedriver.exe");
        String filePath = resource.getFile().getPath();
        System.out.println(filePath);
        System.setProperty("webdriver.chrome.driver", filePath);
    }

}
