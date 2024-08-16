package com.fastcampus.fastcampusprojectboardreview.repository.querydsl;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.fastcampus.fastcampusprojectboardreview.domain.Hashtag;
import com.fastcampus.fastcampusprojectboardreview.domain.QHashtag;

public class HashtagRepositoryCustomImpl extends QuerydslRepositorySupport implements HashtagRepositoryCustom {
	public HashtagRepositoryCustomImpl() {
		super(Hashtag.class);
	}

	@Override
	public List<String> findAllHashtagNames() {
		QHashtag hashtag = QHashtag.hashtag;
		return from(hashtag)
			.select(hashtag.hashtagName)
			.fetch();
	}
}
