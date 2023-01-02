package com.sessionapi.newsscraper.services;

import com.sessionapi.newsscraper.entities.Article;
import com.sessionapi.newsscraper.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        articleRepository.deleteAll();
        return article;
    }

    @Transactional
    public void clean(Article article) {
        articleRepository.deleteAllByTitle(article.getTitle());
                articleRepository.flush();
    }

    public Article read() {

        return null;

    }
}
