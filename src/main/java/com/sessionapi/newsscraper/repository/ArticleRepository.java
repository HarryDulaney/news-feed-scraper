package com.sessionapi.newsscraper.repository;

import com.sessionapi.newsscraper.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ArticleRepository")
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
