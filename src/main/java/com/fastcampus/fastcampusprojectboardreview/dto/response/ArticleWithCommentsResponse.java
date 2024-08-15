package com.fastcampus.fastcampusprojectboardreview.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fastcampus.fastcampusprojectboardreview.dto.ArticleCommentDto;
import com.fastcampus.fastcampusprojectboardreview.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboardreview.dto.HashtagDto;

public record ArticleWithCommentsResponse (
	Long id,
	String title,
	String content,
	Set<String> hashtags,
	LocalDateTime createdAt,
	String email,
	String nickname,
	String userId,
	Set<ArticleCommentResponse> articleCommentsResponse
) {

	public static ArticleWithCommentsResponse of(Long id, String title, String content, Set<String> hashtags, LocalDateTime createdAt, String email, String nickname, String userId, Set<ArticleCommentResponse> articleCommentsResponse) {
		return new ArticleWithCommentsResponse(id, title, content, hashtags, createdAt, email, nickname, userId, articleCommentsResponse);
	}

	public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
		String nickname = dto.userAccountDto().nickname();
		if (nickname == null || nickname.isBlank()) {
			nickname = dto.userAccountDto().userId();
		}

		return new ArticleWithCommentsResponse(
			dto.id(),
			dto.title(),
			dto.content(),
			dto.hashtagDtos().stream().map(HashtagDto::hashtagName).collect(Collectors.toUnmodifiableSet()),
			dto.createdAt(),
			dto.userAccountDto().email(),
			nickname,
			dto.userAccountDto().userId(),
			dto.articleCommentDtos().stream()
				.map(ArticleCommentResponse::from)
				.collect(Collectors.toCollection(LinkedHashSet::new))
		);
	}

}
