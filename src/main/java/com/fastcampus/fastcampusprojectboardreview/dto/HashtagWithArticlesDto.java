package com.fastcampus.fastcampusprojectboardreview.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.fastcampus.fastcampusprojectboardreview.domain.Article;
import com.fastcampus.fastcampusprojectboardreview.domain.Hashtag;

public record HashtagWithArticlesDto(
	Long id,
	Set<ArticleDto> articles,
	String hashtagName,
	LocalDateTime createdAt,
	String createdBy,
	LocalDateTime modifiedAt,
	String modifiedBy
) {
	public static HashtagWithArticlesDto of(Set<ArticleDto> articles, String hashtagName) {
		return new HashtagWithArticlesDto(null, articles, hashtagName, null, null, null, null);
	}

	public static HashtagWithArticlesDto of(Long id, Set<ArticleDto> articles, String hashtagName, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
		return new HashtagWithArticlesDto(id, articles, hashtagName, createdAt, createdBy, modifiedAt, modifiedBy);
	}

	public static HashtagWithArticlesDto from(Hashtag entity) {
		return new HashtagWithArticlesDto(
			entity.getId(),
			entity.getArticles().stream().map(ArticleDto::from).collect(Collectors.toUnmodifiableSet()),
			entity.getHashtagName(),
			entity.getCreatedAt(),
			entity.getCreatedBy(),
			entity.getModifiedAt(),
			entity.getModifiedBy()
		);
	}

	public Hashtag toEntity() {
		return Hashtag.of(hashtagName);
	}
}
