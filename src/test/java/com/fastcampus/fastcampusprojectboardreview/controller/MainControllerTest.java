package com.fastcampus.fastcampusprojectboardreview.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fastcampus.fastcampusprojectboardreview.config.TestSecurityConfig;

@Import(TestSecurityConfig.class)
@WebMvcTest(controllers = MainController.class)
class MainControllerTest {
	private final MockMvc mockMvc;

	MainControllerTest(@Autowired MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test
	void givenNothing_whenRequestingRootPage_thenRedirectsToArticlesPage() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("forward:/articles"))
			.andExpect(forwardedUrl("/articles"))
			.andDo(MockMvcResultHandlers.print());
	}
}