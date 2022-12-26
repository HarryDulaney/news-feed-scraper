package com.sessionapi.newsscraper.repositories;

import com.sessionapi.newsscraper.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ArticleRepository")
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
