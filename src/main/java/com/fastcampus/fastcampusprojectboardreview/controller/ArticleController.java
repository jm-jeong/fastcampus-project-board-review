package com.fastcampus.fastcampusprojectboardreview.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastcampus.fastcampusprojectboardreview.domain.constant.FormStatus;
import com.fastcampus.fastcampusprojectboardreview.domain.constant.SearchType;
import com.fastcampus.fastcampusprojectboardreview.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboardreview.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboardreview.dto.request.ArticleRequest;
import com.fastcampus.fastcampusprojectboardreview.dto.response.ArticleResponse;
import com.fastcampus.fastcampusprojectboardreview.dto.response.ArticleWithCommentsResponse;
import com.fastcampus.fastcampusprojectboardreview.dto.security.BoardPrincipal;
import com.fastcampus.fastcampusprojectboardreview.service.ArticleService;
import com.fastcampus.fastcampusprojectboardreview.service.PaginationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {
	private final ArticleService articleService;
	private final PaginationService paginationService;

	@GetMapping
	public String articles(
		@RequestParam(required = false) SearchType searchType,
		@RequestParam(required = false) String searchValue,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
		ModelMap map
	) {
		Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable)
			.map(ArticleResponse::from);
		List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),
			articles.getTotalPages());

		map.addAttribute("articles", articles);
		map.addAttribute("paginationBarNumbers", barNumbers);
		map.addAttribute("searchTypes", SearchType.values());

		return "articles/index";
	}

	@GetMapping("/{articleId}")
	public String article(@PathVariable("articleId") Long articleId, ModelMap map) {
		ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(
			articleService.getArticleWithComments(articleId));

		map.addAttribute("article", article);
		map.addAttribute("articleComments", article.articleCommentsResponse());
		map.addAttribute("totalCount", articleService.getArticleCount());

		return "articles/detail";
	}

	@GetMapping("/search-hashtag")
	public String searchHashtag(
		@RequestParam(required = false) String searchValue,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
		ModelMap map
	) {
		Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable)
			.map(ArticleResponse::from);
		List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),
			articles.getTotalPages());
		List<String> hashtags = articleService.getHashtags();

		map.addAttribute("articles", articles);
		map.addAttribute("hashtags", hashtags);
		map.addAttribute("paginationBarNumbers", barNumbers);
		map.addAttribute("searchType", SearchType.HASHTAG);

		return "articles/search-hashtag";
	}

	@GetMapping("/form")
	public String articleForm(ModelMap map) {
		map.addAttribute("fromStatus", FormStatus.CREATE);

		return "articles/from";
	}

	@PostMapping("/form")
	public String postNewArticle(
		@AuthenticationPrincipal BoardPrincipal boardPrincipal,
		ArticleRequest articleRequest) {
		articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));

		return "redirect:/articles";
	}

	@GetMapping("/{articleId}/from")
	public String updateArticleForm(
		@PathVariable Long articleId,
		ModelMap map
	) {
		ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));
		map.addAttribute("article", article);
		map.addAttribute("formStatus", FormStatus.UPDATE);

		return "articles/form";
	}

	@PostMapping ("/{articleId}/form")
	public String updateArticle(@PathVariable Long articleId,
		@AuthenticationPrincipal BoardPrincipal boardPrincipal,
		ArticleRequest articleRequest) {
		articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));

		return "redirect:/articles/" + articleId;
	}

	@PostMapping ("/{articleId}/delete")
	public String deleteArticle(@PathVariable Long articleId,
	@AuthenticationPrincipal BoardPrincipal boardPrincipal) {
		articleService.deleteArticle(articleId, boardPrincipal.username());

		return "redirect:/articles";
	}
}
