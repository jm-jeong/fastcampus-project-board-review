package com.fastcampus.fastcampusprojectboardreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;

import com.fastcampus.fastcampusprojectboardreview.domain.QUserAccount;
import com.fastcampus.fastcampusprojectboardreview.domain.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

}
