package com.sessionapi.newsscraper.services;

import com.sessionapi.newsscraper.configurations.CrawlProperties;
import com.sessionapi.newsscraper.entities.Article;
import com.sessionapi.newsscraper.events.CrawlEventPublisher;
import com.sessionapi.newsscraper.models.CrawlSource;
import com.sessionapi.newsscraper.utils.ValidationUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Service("CrawlService")
public class CrawlService {
    private final CrawlSource[] sources;
    private final CrawlProperties crawlProperties;
    private final CrawlEventPublisher crawlEventPublisher;
    private final ScrapeService scrapeService;
    private final ArticleService articleService;

    public CrawlService(ScrapeService scrapeService,
                        ArticleService articleService,
                        CrawlProperties crawlProperties,
                        CrawlEventPublisher crawlEventPublisher) {
        this.crawlProperties = crawlProperties;
        this.crawlEventPublisher = crawlEventPublisher;
        this.scrapeService = scrapeService;
        this.articleService = articleService;
        this.sources = crawlProperties.getSources();
    }


    public void startCrawl() {
        if (!ValidationUtility.isValid(crawlProperties)) {
            crawlEventPublisher.publishErrorEvent("Invalid Crawl Properties from configuration... At least 1 Source property is required.");
        } else {
            int NEXT_SEED_INDEX = 0;
            int totalSources = sources.length;
            while (NEXT_SEED_INDEX < totalSources) {
                crawl(NEXT_SEED_INDEX);
                NEXT_SEED_INDEX++;
            }
            crawlEventPublisher.publishCrawlEnding("End has been reached... all sources are crawled.");
        }

    }

    private void crawl(int seedIndex) {
        CrawlSource source = sources[seedIndex];
        log.info("Crawling new seed source: " + source.getName());
        Set<String> subLinks = scrapeService.getSourceUrls(source);
        log.info("Seed source has this many unique links to scrape #: " + subLinks.size());
        for (String link : subLinks) {
            log.info("Scraping from: " + link);
            Article article = scrapeService.scrape(link, source);
            if (article == null) {
                continue;
            }

            articleService.clean(article);
            articleService.save(article);

        }
    }
}
