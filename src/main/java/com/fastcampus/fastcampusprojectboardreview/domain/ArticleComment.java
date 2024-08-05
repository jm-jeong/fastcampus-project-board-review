package com.fastcampus.fastcampusprojectboardreview.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
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
