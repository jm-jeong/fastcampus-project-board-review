package com.fastcampus.fastcampusprojectboardreview.config;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import com.fastcampus.fastcampusprojectboardreview.domain.UserAccount;
import com.fastcampus.fastcampusprojectboardreview.repository.UserAccountRepository;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

	@MockBean
	private UserAccountRepository userAccountRepository;

	@BeforeTestMethod
	public void securitySetUp() {
		given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
			"unoTest",
			"pw",
			"uno-test@email.com",
			"uno-test",
			"test memo"
		)));
	}

}
