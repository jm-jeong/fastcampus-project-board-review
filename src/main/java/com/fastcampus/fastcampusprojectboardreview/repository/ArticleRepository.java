package com.fastcampus.fastcampusprojectboardreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.fastcampusprojectboardreview.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
