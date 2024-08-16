package com.fastcampus.fastcampusprojectboardreview.repository.querydsl;

import java.util.List;

import com.fastcampus.fastcampusprojectboardreview.domain.Hashtag;

public interface HashtagRepositoryCustom {
	List<String> findAllHashtagNames();
}
