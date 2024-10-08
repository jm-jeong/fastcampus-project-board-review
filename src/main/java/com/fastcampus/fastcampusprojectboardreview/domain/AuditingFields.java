package com.fastcampus.fastcampusprojectboardreview.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditingFields {
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreatedDate
	@Column(nullable = false, updatable = false)
	protected LocalDateTime createdAt;

	@CreatedBy
	@Column(nullable = false, updatable = false, length = 100)
	protected String createdBy;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@LastModifiedDate
	@Column(nullable = false)
	protected LocalDateTime modifiedAt;

	@LastModifiedBy
	@Column(nullable = false, length = 100)
	protected String modifiedBy;
}
