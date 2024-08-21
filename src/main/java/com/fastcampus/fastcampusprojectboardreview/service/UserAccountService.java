package com.fastcampus.fastcampusprojectboardreview.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.fastcampusprojectboardreview.domain.UserAccount;
import com.fastcampus.fastcampusprojectboardreview.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboardreview.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountService {
	private final UserAccountRepository userAccountRepository;

	@Transactional(readOnly = true)
	public Optional<UserAccountDto> searchUser(String username) {
		return userAccountRepository.findById(username).map(UserAccountDto::from);
	}

	public UserAccountDto saveUser(String username, String password, String email, String nickname, String memo) {
		return UserAccountDto.from(
			userAccountRepository.save(UserAccount.of(username, password, email, nickname, memo, username))
		);
	}
}
