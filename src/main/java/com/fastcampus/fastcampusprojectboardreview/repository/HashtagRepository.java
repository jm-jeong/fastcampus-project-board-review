package com.fastcampus.fastcampusprojectboardreview.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.fastcampus.fastcampusprojectboardreview.domain.Hashtag;
import com.fastcampus.fastcampusprojectboardreview.repository.querydsl.HashtagRepositoryCustom;

public interface HashtagRepository extends
	JpaRepository<Hashtag, Long>,
	HashtagRepositoryCustom,
	QuerydslPredicateExecutor<Hashtag>
{
	Optional<Hashtag> findByHashtagName(String hashtagName);

	List<Hashtag> findByHashtagNameIn(Set<String> hashtagNames);
}
