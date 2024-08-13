package com.fastcampus.fastcampusprojectboardreview.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fastcampus.fastcampusprojectboardreview.dto.ArticleDto;

public record ArticleResponse (
	Long id,
	String title,
	String content,
	String hashtag,
	LocalDateTime createdAt,
	String email,
	String nickname
) {

	public static ArticleResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
		return new ArticleResponse(id, title, content, hashtag, createdAt, email, nickname);
	}

	public static ArticleResponse from(ArticleDto dto) {
		String nickname = dto.userAccountDto().nickname();
		if (nickname == null || nickname.isBlank()) {
			nickname = dto.userAccountDto().userId();
		}

		return ArticleResponse.of(
			dto.id(),
			dto.title(),
			dto.content(),
			dto.hashtag(),
			dto.createdAt(),
			dto.userAccountDto().email(),
			nickname
		);

	}
}
