package com.sessionapi.newsscraper.services;

import com.sessionapi.newsscraper.entities.Article;
import com.sessionapi.newsscraper.utils.ValidationUtility;
import com.sessionapi.newsscraper.models.CrawlSource;
import com.sessionapi.newsscraper.events.CrawlEventPublisher;
import com.sessionapi.newsscraper.configurations.CrawlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service("CrawlService")
public class CrawlService {
    private static int NEXT_SEED_INDEX = -1;
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


    public void startCrawl() throws IOException {
        if (!ValidationUtility.isValid(crawlProperties)) {
            crawlEventPublisher.publishErrorEvent("Invalid Crawl Properties from configuration... At least 1 Source property is required.");
        } else {
            NEXT_SEED_INDEX = 0;
            CrawlSource source = sources[NEXT_SEED_INDEX];
            List<String> subLinks = scrapeService.getSourceUrls(source);
            for (String link : subLinks) {
                try {
                    Article article = scrapeService.scrape(link, source);
                    articleService.save(article);

                } catch (Exception exception) {
                    exception.printStackTrace();
                    log.info("Unable to scrape link: " + link);
                }
            }
            crawlEventPublisher.publishCrawlNext("Crawl Next");
        }

    }

    public void crawlNext() throws IOException {
        NEXT_SEED_INDEX++;
        if (!ValidationUtility.hasNextCrawl(NEXT_SEED_INDEX, sources)) {
            crawlEventPublisher.publishCrawlEnding("End of Seed Urls reached.");
        } else {
            CrawlSource source = sources[NEXT_SEED_INDEX];
            List<String> subLinks = scrapeService.getSourceUrls(source);
            for (String link : subLinks) {
                try {
                    Article article = scrapeService.scrape(link, source);
                    articleService.save(article);

                } catch (Exception exception) {
                    exception.printStackTrace();
                    log.info("Unable to scrape link: " + link);
                }
            }

            crawlEventPublisher.publishCrawlNext("Crawl Next");

        }
    }

    public CrawlSource getCurrentSource() {
        return sources[NEXT_SEED_INDEX];
    }
}
