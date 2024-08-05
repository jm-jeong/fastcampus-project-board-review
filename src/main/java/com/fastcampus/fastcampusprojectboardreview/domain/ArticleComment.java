package com.fastcampus.fastcampusprojectboardreview.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
	@Index(columnList = "content"),
	@Index(columnList = "createdAt"),
	@Index(columnList = "createdBy")
})
@Entity
public class ArticleComment extends AuditingFields{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "article_comment_id")
	private Long id;

	@Setter
	@Column(nullable = false, length = 500)
	private String content;

	@Setter
	@ManyToOne(optional = false)
	private Article article;


	private ArticleComment(String content, Article article) {
		this.content = content;
		this.article = article;
	}

	public static ArticleComment of(String content, Article article) {
		return new ArticleComment(content, article);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ArticleComment that))
			return false;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
