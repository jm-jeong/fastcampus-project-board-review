package com.fastcampus.fastcampusprojectboardreview.dto.response;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fastcampus.fastcampusprojectboardreview.dto.ArticleCommentDto;
import com.fastcampus.fastcampusprojectboardreview.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboardreview.dto.HashtagDto;

public record ArticleWithCommentsResponse(
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

	public static ArticleWithCommentsResponse of(Long id, String title, String content, Set<String> hashtags,
		LocalDateTime createdAt, String email, String nickname, String userId,
		Set<ArticleCommentResponse> articleCommentsResponse) {
		return new ArticleWithCommentsResponse(id, title, content, hashtags, createdAt, email, nickname, userId,
			articleCommentsResponse);
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
			organizeChildComments(dto.articleCommentDtos())
		);
	}

	private static Set<ArticleCommentResponse> organizeChildComments(Set<ArticleCommentDto> dtos) {
		Map<Long, ArticleCommentResponse> map = dtos.stream()
			.map(ArticleCommentResponse::from)
			.collect(Collectors.toMap(ArticleCommentResponse::id, Function.identity()));

		map.values().stream()
			.filter(ArticleCommentResponse::hasParentComment)
			.forEach(comment -> {
				ArticleCommentResponse parentComment = map.get(comment.parentCommentId());
				parentComment.childComments().add(comment);
			});

		return map.values().stream()
			.filter(comment -> !comment.hasParentComment())
			.collect(Collectors.toCollection(() ->
				new TreeSet<>(Comparator
					.comparing(ArticleCommentResponse::createdAt)
					.reversed()
					.thenComparingLong(ArticleCommentResponse::id)
				)
			));
	}
}
