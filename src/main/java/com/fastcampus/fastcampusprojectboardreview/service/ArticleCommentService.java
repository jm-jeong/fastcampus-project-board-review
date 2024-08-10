package com.fastcampus.fastcampusprojectboardreview.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.fastcampusprojectboardreview.dto.ArticleCommentDto;
import com.fastcampus.fastcampusprojectboardreview.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboardreview.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleCommentService {
	private final ArticleRepository articleRepository;
	private final ArticleCommentRepository articleCommentRepository;

	@Transactional(readOnly = true)
	public List<ArticleCommentDto> searchArticleComments(Long articleId) {
		return List.of();
	}

	public void saveArticleComment(ArticleCommentDto articleCommentDto) {}

	public void updateArticleComment(ArticleCommentDto articleCommentDto) {}

	public void deleteArticleComment(Long articleCommentId) {}
}
