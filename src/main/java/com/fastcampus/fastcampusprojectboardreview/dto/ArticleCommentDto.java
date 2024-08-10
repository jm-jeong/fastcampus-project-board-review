package com.fastcampus.fastcampusprojectboardreview.dto;

import java.time.LocalDateTime;

import com.fastcampus.fastcampusprojectboardreview.domain.Article;
import com.fastcampus.fastcampusprojectboardreview.domain.ArticleComment;

/**
 * DTO for {@link ArticleComment}
 */
public record ArticleCommentDto(
	Long id,
	Long articleId,
	UserAccountDto userAccountDto,
	String content,
	LocalDateTime createdAt,
	String createdBy,
	LocalDateTime modifiedAt,
	String modifiedBy
) {
	public static ArticleCommentDto of(Long id, Long articleId, UserAccountDto userAccountDto, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
		return new ArticleCommentDto(id, articleId, userAccountDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
	}

	public static ArticleCommentDto from(ArticleComment entity) {
		return new ArticleCommentDto(
			entity.getId(),
			entity.getArticle().getId(),
			UserAccountDto.from(entity.getUserAccount()),
			entity.getContent(),
			entity.getCreatedAt(),
			entity.getCreatedBy(),
			entity.getModifiedAt(),
			entity.getModifiedBy()
		);
	}

	public ArticleComment toEntity(Article entity) {
		return ArticleComment.of(
			entity,
			userAccountDto.toEntity(),
			content
		);
	}
}