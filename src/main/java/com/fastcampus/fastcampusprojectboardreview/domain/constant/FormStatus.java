package com.fastcampus.fastcampusprojectboardreview.domain.constant;

import org.springframework.data.annotation.CreatedBy;

import lombok.Getter;

@Getter
public enum FormStatus {
	CREATE("저장", false),
	UPDATE("수정", true);

	private final String description;
	private final Boolean update;

	FormStatus(String description, Boolean update) {
		this.description = description;
		this.update = update;
	}
}
