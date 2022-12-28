package com.sessionapi.newsscraper.services;

import com.sessionapi.newsscraper.entities.Article;
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
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.time.Duration;

@Slf4j
@Service("ScrapeService")
public class ScrapeService {
    private WebDriver webDriver;

    public ScrapeService() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setImplicitWaitTimeout(Duration.ofMillis(2000));
        webDriver = new ChromeDriver(options);
    }

    public List<String> getSourceUrls(CrawlSource source) {
        webDriver.get(source.getUrl());
        return findArticleUrls(source);
    }

    private List<String> findArticleUrls(CrawlSource crawlSource) {
        List<String> articleLinks = new ArrayList<>();
        By byAnchor = By.xpath(crawlSource.getStartPointXPath());
        List<WebElement> articlesList = new ArrayList<>();
        int lastY = 0;

        while (articlesList.size() < crawlSource.getTargetArticleCount()) {
            articlesList = webDriver.findElements(byAnchor);
            WebElement lastElement = articlesList.get(articlesList.size() - 1);
            Actions actions = new Actions(webDriver);
            actions.moveToElement(lastElement);
            actions.perform();

        }
        addScannedLinks(articlesList, crawlSource, articleLinks);

        return articleLinks;
    }

    public Article scrape(String href, CrawlSource source) throws Exception {
//        HtmlPage page = webClient.getPage(href);
//        Article article = new Article();
//        article.setOriginUrl(href);
//        article.setScannedTimestamp(ScrapeUtility.timeStampNow());
//        HtmlArticle articleElement = ScrapeUtility.getArticleElement(source.getTargetSelector(), source.getTargetSelectorType(), page);
//        article.setHtmlContent(articleElement.asXml());
//        HtmlElement titleElement = ScrapeUtility.getTitleElement(source.getTitleSelector(), source.getTitleSelectorType(), articleElement);
//        article.setTitle(titleElement.asNormalizedText());
//        HtmlElement authorElement = ScrapeUtility.getAuthorElement(source.getAuthorSelector(), source.getAuthorSelectorType(), articleElement);
//        article.setAuthorFullName(authorElement.asNormalizedText());
//        HtmlImage htmlImage = ScrapeUtility.getImageElement(source.getImageSelector(), source.getImageSelectorType(), articleElement);
//        article.setImageSource(htmlImage.getSrc());
//        article.setSourcePublisher(source.getName());
//        HtmlElement dateElement = ScrapeUtility.getDateElement(source.getDateSelector(), source.getDateSelectorType(), articleElement);
//        article.setPublishDate(dateElement.asNormalizedText());
//        if (!ValidationUtility.isValid(article)) {
//            throw new Exception("Unable to scrape the Article properly.");
//        }
//
//        return article;
        return null;
    }

    private void addScannedLinks(List<WebElement> links, CrawlSource crawlSource, List<String> resultLinks) {
        for (WebElement e : links) {
            WebElement element = e.findElement(By.tagName("a"));
            if (ValidationUtility.isValidArticleLink(element, crawlSource)) {
                String href = element.getDomAttribute("href");
                // Append base url in order to scrape later
                if (!href.startsWith(crawlSource.getUrl())) {
                    href = crawlSource.getUrl() + element.getDomAttribute("href");
                }
                resultLinks.add(href);

            }


        }
    }


}