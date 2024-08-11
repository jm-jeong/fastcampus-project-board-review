package com.fastcampus.fastcampusprojectboardreview.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fastcampus.fastcampusprojectboardreview.config.SecurityConfig;

@DisplayName("View Controller 인증")
@Import(SecurityConfig.class)
@WebMvcTest
public class AuthControllerTest {
	private final MockMvc mockMvc;

	public AuthControllerTest(@Autowired MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@DisplayName("[view][GET] 로그인 페이지 - 정상 호출")
	@Test
	public void givenNothing_whenTryingToLogin_thenReturnLoginView() throws Exception {
		//when & then
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andDo(MockMvcResultHandlers.print());
	}
}
