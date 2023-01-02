package com.sessionapi.newsscraper.services;

import com.sessionapi.newsscraper.entities.Article;
import com.sessionapi.newsscraper.events.CrawlEventPublisher;
import com.sessionapi.newsscraper.models.CrawlSource;
import com.sessionapi.newsscraper.utils.ScrapeUtility;
import com.sessionapi.newsscraper.utils.ValidationUtility;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service("ScrapeService")
public class ScrapeService {
    private ChromeOptions chromeOptions;
    private WebDriver driver;
    @Autowired
    private CrawlEventPublisher eventPublisher;

    public ScrapeService() {
        chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.setImplicitWaitTimeout(Duration.ofMillis(500));
        driver = new ChromeDriver(chromeOptions);
    }

    public Set<String> getSourceUrls(CrawlSource source) {
        driver.get(source.getUrl());
        return findArticleUrls(source, driver);
    }

    private Set<String> findArticleUrls(CrawlSource crawlSource, WebDriver driver) {
        Set<String> articleLinks = new HashSet<>();
        By byAnchor = By.xpath(crawlSource.getStartPointXPath());
        List<WebElement> articlesList = new ArrayList<>();

        while (articlesList.size() < crawlSource.getTargetArticleCount()) {
            articlesList = driver.findElements(byAnchor);
            WebElement lastElement = articlesList.get(articlesList.size() - 1);
            Actions actions = new Actions(driver);
            actions.moveToElement(lastElement);
            actions.perform();
        }

        addScannedLinks(articlesList, crawlSource, articleLinks);

        return articleLinks;
    }

    public Article scrape(String href, CrawlSource source) {
        Article article = new Article();
        driver.get(href);
        article.setScrapeOriginUrl(href);
        article.setScannedTimestamp(ScrapeUtility.timeStampNow());
        WebElement articleElement = driver.findElement(By.tagName(source.getArticleElement()));
        article.setHtmlContent(articleElement.getAttribute("outerHTML"));
        article.setOriginUrl(articleElement.findElement(By.xpath(source.getOriginXPath())).getDomAttribute("href"));
        article.setTitle(articleElement.findElement(By.xpath(source.getTitleXPath())).getText());
        article.setAuthorFullName(articleElement.findElement(By.xpath(source.getAuthorXPath())).getText());
        article.setPublishDate(articleElement.findElement(By.xpath(source.getDateXPath())).getText());
        String imgSource;
        try {
            imgSource = articleElement.findElement(By.xpath(source.getImageXPath())).getAttribute("src");
            article.setImageSource(imgSource);

        } catch (Exception noSuchElementException) {
            article.setImageSource("placeholder-image-src");
        }

        if (source.isHasContextJson()) {
            String[] categories = ScrapeUtility.getKeywordsFromContext(article, source);
            article.setCategories(categories);
        }

        if (!ValidationUtility.isValid(article)) {
            eventPublisher.publishErrorEvent("Unable to scrape the Article properly.");
            return null;
        }

        return article;
    }

    private void addScannedLinks(List<WebElement> links, CrawlSource crawlSource, Set<String> resultLinks) {
        for (WebElement e : links) {
            WebElement element = e.findElement(By.tagName("a"));
            if (ValidationUtility.isValidArticleLink(element, crawlSource)) {
                String href = element.getDomAttribute("href");
                // Append base url in order to scrape later
                if (!href.startsWith(crawlSource.getUrl())) {
                    if (crawlSource.getName().equals("Yahoo")) {
                        String subString = element.getDomAttribute("href").substring(5);
                        href = crawlSource.getUrl() + subString;
                    }
                }
                resultLinks.add(href);

            }
        }
    }


    public void refresh() {
        driver = new ChromeDriver(chromeOptions);
    }

    public void endSession() {
        driver.close();
    }
}