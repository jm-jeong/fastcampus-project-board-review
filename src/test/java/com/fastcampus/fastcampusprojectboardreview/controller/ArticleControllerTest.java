package com.fastcampus.fastcampusprojectboardreview.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fastcampus.fastcampusprojectboardreview.config.SecurityConfig;
import com.fastcampus.fastcampusprojectboardreview.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboardreview.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboardreview.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboardreview.service.ArticleService;
import com.fastcampus.fastcampusprojectboardreview.service.PaginationService;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
	private final MockMvc mvc;

	@MockBean
	ArticleService articleService;
	@MockBean
	PaginationService paginationService;

	ArticleControllerTest(@Autowired MockMvc mockMvc) {
		this.mvc = mockMvc;
	}

	@DisplayName("[view][GET] 게시판 리스트 - 게시 페이지 정상 호출")
	@Test
	void givenNothing_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {
		//given
		given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
		given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

		//When & Then
		mvc.perform(get("/articles"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(view().name("articles/index"))
			.andExpect(model().attributeExists("articles"))
			.andExpect(model().attributeExists("paginationBarNumbers"))
			.andExpect(model().attributeExists("searchTypes"));

		then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
		then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
	}

	@DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 검색어와 함께 호출")
	@Test
	public void givenSearchKeyword_whenSearchingArticlesView_thenReturnsArticlesView() throws Exception {
		//Given
		SearchType searchType = SearchType.TITLE;
		String searchValue = "title";
		given(articleService.searchArticles(eq(searchType), eq(searchValue), any(Pageable.class))).willReturn(
			Page.empty());
		given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

		//When
		mvc.perform(get("/articles")
				.queryParam("searchType", searchType.name())
				.queryParam("searchValue", searchValue)
			)
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(view().name("articles/index"))
			.andExpect(model().attributeExists("articles"))
			.andExpect(model().attributeExists("searchTypes"));
		//Then
		then(articleService).should().searchArticles(eq(searchType), eq(searchValue), any(Pageable.class));
		then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());

	}

	@DisplayName("[view][GET] 게시글 페이지 - 정상 호출")
	@Test
	public void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
		// Given
		Long articleId = 1L;
		long totalCount = 1L;
		given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());
		given(articleService.getArticleCount()).willReturn(totalCount);

		// When & Then
		mvc.perform(get("/articles/" + articleId))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(view().name("articles/detail"))
			.andExpect(model().attributeExists("article"))
			.andExpect(model().attributeExists("articleComments"))
			.andExpect(model().attributeExists("articleComments"))
			.andExpect(model().attribute("totalCount", totalCount));
		then(articleService).should().getArticle(articleId);
		then(articleService).should().getArticleCount();
	}

	@Disabled("구현 중")
	@DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
	@Test
	public void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
		// Given

		// When & Then
		mvc.perform(get("/articles/search"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(view().name("articles/search"));
	}

	@DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
	@Test
	public void givenNothing_whenRequestingArticleSearchHashtagView_thenReturnsArticleSearchHashtagView() throws Exception {
		// Given
		List<String> hashtags = List.of("#java", "#spring", "#boot");
		given(articleService.searchArticlesViaHashtag(eq(null), any(Pageable.class))).willReturn(Page.empty());
		given(articleService.getHashtags()).willReturn(hashtags);
		given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(1, 2, 3, 4, 5));

		// When & Then
		mvc.perform(get("/articles/search-hashtag"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(view().name("articles/search-hashtag"))
			.andExpect(model().attribute("articles", Page.empty()))
			.andExpect(model().attribute("hashtags", hashtags))
			.andExpect(model().attributeExists("paginationBarNumbers"))
			.andExpect(model().attribute("searchType", SearchType.HASHTAG));
		then(articleService).should().searchArticlesViaHashtag(eq(null), any(Pageable.class));
		then(articleService).should().getHashtags();
		then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
	}

	@DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출, 해시태그 입력")
	@Test
	public void givenHashtag_whenRequestingArticleSearchHashtagView_thenReturnsArticleSearchHashtagView() throws Exception {
		// Given
		String hashtag = "#java";
		List<String> hashtags = List.of("#java", "#spring", "#boot");
		given(articleService.searchArticlesViaHashtag(eq(hashtag), any(Pageable.class))).willReturn(Page.empty());
		given(articleService.getHashtags()).willReturn(hashtags);
		given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(1, 2, 3, 4, 5));

		// When & Then
		mvc.perform(
				get("/articles/search-hashtag")
					.queryParam("searchValue", hashtag)
			)
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(view().name("articles/search-hashtag"))
			.andExpect(model().attribute("articles", Page.empty()))
			.andExpect(model().attribute("hashtags", hashtags))
			.andExpect(model().attributeExists("paginationBarNumbers"))
			.andExpect(model().attribute("searchType", SearchType.HASHTAG));
		then(articleService).should().searchArticlesViaHashtag(eq(hashtag), any(Pageable.class));
		then(articleService).should().getHashtags();
		then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
	}



	private ArticleWithCommentsDto createArticleWithCommentsDto() {
		return ArticleWithCommentsDto.of(
			1L,
			createUserAccountDto(),
			Set.of(),
			"title",
			"content",
			"#java",
			LocalDateTime.now(),
			"uno",
			LocalDateTime.now(),
			"uno"
		);
	}

	private UserAccountDto createUserAccountDto() {
		return UserAccountDto.of(
			"uno",
			"pw",
			"uno@mail.com",
			"Uno",
			"memo",
			LocalDateTime.now(),
			"uno",
			LocalDateTime.now(),
			"uno"
		);
	}
}