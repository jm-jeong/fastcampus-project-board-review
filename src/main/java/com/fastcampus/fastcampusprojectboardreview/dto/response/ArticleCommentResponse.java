package com.fastcampus.fastcampusprojectboardreview.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.fastcampus.fastcampusprojectboardreview.dto.ArticleCommentDto;

public record ArticleCommentResponse(
	Long id,
	String content,
	LocalDateTime createdAt,
	String email,
	String nickname,
	String userId,
	Long parentCommentId,
	Set<ArticleCommentResponse> childComments) {

	public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId, Long parentCommentId) {
		Comparator<ArticleCommentResponse> childCommentComparator = Comparator.comparing(
				ArticleCommentResponse::createdAt)
			.thenComparingLong(ArticleCommentResponse::parentCommentId);

		return new ArticleCommentResponse(id, content, createdAt, email, nickname, userId, parentCommentId,new TreeSet<>(childCommentComparator));
	}

	public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
		return ArticleCommentResponse.of(id, content, createdAt, email, nickname, userId, null);
	}

	public static ArticleCommentResponse from(ArticleCommentDto dto) {
		String nickname = dto.userAccountDto().nickname();
		if (nickname == null || nickname.isBlank()) {
			nickname = dto.userAccountDto().userId();
		}
		return ArticleCommentResponse.of(
			dto.id(),
			dto.content(),
			dto.createdAt(),
			dto.userAccountDto().email(),
			nickname,
			dto.userAccountDto().userId(),
			dto.parentCommentId()
		);
	}

	public boolean hasParentComment() {
		return parentCommentId != null;
	}
}
