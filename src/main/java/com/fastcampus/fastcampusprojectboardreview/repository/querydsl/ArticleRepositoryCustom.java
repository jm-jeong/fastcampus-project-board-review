package com.fastcampus.fastcampusprojectboardreview.repository.querydsl;

import java.util.List;

import com.fastcampus.fastcampusprojectboardreview.domain.Article;

public interface ArticleRepositoryCustom {
	List<String> findAllDistinctHashtags();
}
