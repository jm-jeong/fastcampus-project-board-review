package com.fastcampus.fastcampusprojectboardreview.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@DisplayName("Data REST - API 테스트")
@AutoConfigureMockMvc
@SpringBootTest
 class DataRestTest {
	private final MockMvc mockMvc;

	 DataRestTest(@Autowired MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@DisplayName("[api] 게시글 리스트 조회")
	@Test
	void givenNothing_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {
		//Given

		//When & Then
		mockMvc.perform(get("/api/articles"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}

	@DisplayName("[api] 게시글 단건 조회")
	@Test
	void givenNothing_whenRequestingArticle_thenReturnsArticlesJsonResponse() throws Exception {
		mockMvc.perform(get("/api/articles/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}

	@DisplayName("[api] 게시글 -> 코멘트 조회")
	@Test
	void givenNothing_whenRequestingArticleCommentsFromArticle_thenReturnsArticlesJsonResponse() throws Exception {
		mockMvc.perform(get("/api/articles/1/articleComments"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}

	@DisplayName("[api] 게시글 코멘트 조회")
	@Test
	void givenNothing_whenRequestingArticleComments_thenReturnsArticlesJsonResponse() throws Exception {
		//Given

		//When & Then
		mockMvc.perform(get("/api/articleComments"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}

	@DisplayName("[api] 게시글 단건 조회")
	@Test
	void givenNothing_whenRequestingArticleComment_thenReturnsArticlesJsonResponse() throws Exception {
		mockMvc.perform(get("/api/articleComments/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}


}
