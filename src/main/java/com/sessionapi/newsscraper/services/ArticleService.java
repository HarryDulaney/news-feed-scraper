package com.sessionapi.newsscraper.services;

import com.sessionapi.newsscraper.entities.Article;
import com.sessionapi.newsscraper.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

@Service("ArticleService")
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void save(Article article) {
        articleRepository.saveAndFlush(article);
    }

    public Article delete(Article article) {
        return article;
    }

    public Article read() {

        return null;

    }
}
