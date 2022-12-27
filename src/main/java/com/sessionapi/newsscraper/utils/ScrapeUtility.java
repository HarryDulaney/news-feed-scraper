package com.sessionapi.newsscraper.utils;

import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLImageElement;
import com.sessionapi.newsscraper.common.Constants;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class ScrapeUtility {

    public static HtmlElement findStartingPointElement(String startPointXPath, HtmlPage page) {
        return page.getFirstByXPath(startPointXPath);
    }

    public static HtmlElement getDateElement(String selector,
                                             String selectorType,
                                             HtmlElement article) {
        HtmlElement element = null;
        switch (selectorType) {
            case Constants.SELECT_TYPE_ELEMENT:
                List<HtmlElement> list = article.getElementsByTagName(selector);
                if (list.size() > 0) {
                    element = list.get(0);
                }
                break;
            case Constants.SELECT_TYPE_ID:
                element = article.getElementsByAttribute("*", "id", selector).get(0);
                break;
            case Constants.SELECT_TYPE_CLASS:
                element = article.getElementsByAttribute("*", "class", selector).get(0);
                break;
        }
        return element;
    }

    public static HtmlElement getAuthorElement(String selector,
                                               String selectorType,
                                               HtmlElement article) {
        HtmlElement element = null;
        switch (selectorType) {
            case Constants.SELECT_TYPE_ELEMENT:
                element = article.getEnclosingElement(selector);
                break;
            case Constants.SELECT_TYPE_ID:
                element = article.getElementsByAttribute("div", "id", selector).get(0);
                break;
            case Constants.SELECT_TYPE_CLASS:
                element = article.getElementsByAttribute("div", "class", selector).get(0);
                break;
            case Constants.SELECT_TYPE_XPATH:
                element = article.getFirstByXPath(selector);
        }
        return element;
    }

    public static HtmlArticle getArticleElement(String targetSelector,
                                                String selectorType,
                                                HtmlPage page) {
        HtmlArticle element = null;
        switch (selectorType) {
            case Constants.SELECT_TYPE_ELEMENT:
                element = (HtmlArticle) page.getElementsByTagName(targetSelector).get(0);
                break;
            case Constants.SELECT_TYPE_ID:
                element = (HtmlArticle) page.getElementById(targetSelector);
                break;
            case Constants.SELECT_TYPE_XPATH:
                element = page.getFirstByXPath(targetSelector);
                break;
        }
        return element;
    }

    public static HtmlElement getParentElement(String parentSelector,
                                               String parentSelectorType, DomElement nestedElement) {
        HtmlElement element = null;
        switch (parentSelectorType) {
            case Constants.SELECT_TYPE_ELEMENT:
                element = (HtmlArticle) nestedElement.getElementsByTagName(parentSelector).get(0);
                break;
            case Constants.SELECT_TYPE_XPATH:
                element = nestedElement.getFirstByXPath(parentSelector);
                break;
        }
        return element;
    }

    public static HtmlElement getTitleElement(String titleSelector,
                                              String titleSelectorType,
                                              HtmlArticle articleElement) {
        HtmlElement element = null;
        switch (titleSelectorType) {
            case Constants.SELECT_TYPE_ELEMENT:
                element = articleElement.getEnclosingElement(titleSelector);
                break;
            case Constants.SELECT_TYPE_XPATH:
                element = articleElement.getFirstByXPath(titleSelector);
                break;
        }
        return element;
    }

    public static Timestamp timeStampNow() {
        return Timestamp.from(Instant.now());
    }

    public static HtmlImage getImageElement(String imageSelector,
                                            String imageSelectorType,
                                            HtmlArticle articleElement) {
        HtmlImage image = null;
        switch (imageSelectorType) {
            case Constants.SELECT_TYPE_ELEMENT:
                image = (HtmlImage) articleElement.getEnclosingElement(imageSelector);
                break;
            case Constants.SELECT_TYPE_ID:
                image = (HtmlImage) articleElement.getElementsByAttribute("img", "id", imageSelector).get(0);
                break;
            case Constants.SELECT_TYPE_XPATH:
                image = (HtmlImage) articleElement.getFirstByXPath(imageSelector);
                break;
        }
        return image;
    }
}
