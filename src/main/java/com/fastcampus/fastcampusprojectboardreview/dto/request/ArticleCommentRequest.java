package com.fastcampus.fastcampusprojectboardreview.dto.request;

import java.io.Serializable;

import com.fastcampus.fastcampusprojectboardreview.dto.ArticleCommentDto;
import com.fastcampus.fastcampusprojectboardreview.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboardreview.dto.UserAccountDto;

/**
 * DTO for {@link com.fastcampus.fastcampusprojectboardreview.domain.ArticleComment}
 */
public record ArticleCommentRequest(
	Long articleId,
	String content
) {
	public static ArticleCommentRequest of(Long articleId, String content) {
		return new ArticleCommentRequest(articleId, content);
	}

	public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
		return ArticleCommentDto.of(articleId, userAccountDto, content);
	}
}