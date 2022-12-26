package com.sessionapi.newsscraper.services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLImageElement;
import com.sessionapi.newsscraper.entities.Article;
import com.sessionapi.newsscraper.models.CrawlSource;
import com.sessionapi.newsscraper.utils.ScrapeUtility;
import com.sessionapi.newsscraper.utils.ValidationUtility;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("ScrapeService")
public class ScrapeService {
    private WebClient webClient;

    public ScrapeService() {
        this.webClient = new WebClient();
    }

    public List<String> getSourceUrls(CrawlSource source) throws IOException {
        HtmlPage seedPage = webClient.getPage(source.getUrl());
        webClient.waitForBackgroundJavaScriptStartingBefore(1000);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        return findArticleUrls(source, seedPage);
    }

    private List<String> findArticleUrls(CrawlSource crawlSource, HtmlPage page) {
        List<String> articleLinks = new ArrayList<>();
        HtmlElement parentListElement = ScrapeUtility.getParentElement(crawlSource.getParentSelector(), crawlSource.getParentSelectorType(), page);
        List<DomNode> articleElements = parentListElement.querySelectorAll("a"); // Get <a> element tags from under <ul>
        for (DomNode domNode : articleElements) {
            HtmlAnchor anchor = (HtmlAnchor) domNode;
            if (ValidationUtility.isValidArticleLink(anchor, crawlSource)) {
                String href = anchor.getHrefAttribute();
                if (!anchor.getHrefAttribute().startsWith(crawlSource.getUrl())) {
                    href = crawlSource.getUrl() + anchor.getHrefAttribute();
                }
                articleLinks.add(href);
            }
        }
        return articleLinks;
    }

    public Article scrape(String href, CrawlSource source) throws Exception {
        HtmlPage page = webClient.getPage(href);
        Article article = new Article();
        article.setOriginUrl(href);
        article.setScannedTimestamp(ScrapeUtility.timeStampNow());
        HtmlArticle articleElement = ScrapeUtility.getArticleElement(source.getTargetSelector(), source.getTargetSelectorType(), page);
        article.setHtmlContent(articleElement.asXml());
        HtmlElement titleElement = ScrapeUtility.getTitleElement(source.getTitleSelector(), source.getTitleSelectorType(), articleElement);
        article.setTitle(titleElement.asNormalizedText());
        HtmlElement authorElement = ScrapeUtility.getAuthorElement(source.getAuthorSelector(), source.getAuthorSelectorType(), articleElement);
        article.setAuthorFullName(authorElement.asNormalizedText());
        HtmlImage htmlImage = ScrapeUtility.getImageElement(source.getImageSelector(), source.getImageSelectorType(), articleElement);
        article.setImageSource(htmlImage.getSrc());
        article.setSourcePublisher(source.getName());
        HtmlElement dateElement = ScrapeUtility.getDateElement(source.getDateSelector(), source.getDateSelectorType(), articleElement);
        article.setPublishDate(dateElement.asNormalizedText());
        if (!ValidationUtility.isValid(article)) {
            throw new Exception("Unable to scrape the Article properly.");
        }

        return article;
    }


}