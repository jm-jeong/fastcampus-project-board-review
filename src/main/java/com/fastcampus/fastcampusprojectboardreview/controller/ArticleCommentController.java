package com.fastcampus.fastcampusprojectboardreview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastcampus.fastcampusprojectboardreview.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboardreview.dto.request.ArticleCommentRequest;
import com.fastcampus.fastcampusprojectboardreview.service.ArticleCommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
	private final ArticleCommentService articleCommentService;

	@PostMapping("/new")
	public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
		//TODO 인증 정보 넣어줘야함
		articleCommentService.saveArticleComment(articleCommentRequest.toDto(
			UserAccountDto.of("uno", "pw", "uno@mail.com", null, null)
		));

		return "redirect:/articles/" + articleCommentRequest.articleId();
	}

	@PostMapping("/{commentId}/delete")
	public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
		articleCommentService.deleteArticleComment(commentId);

		return "redirect:/articles/" + articleId;
	}
}
