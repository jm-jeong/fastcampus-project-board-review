package com.fastcampus.fastcampusprojectboardreview.domain;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
	@Index(columnList = "title"),
	@Index(columnList = "hashtag"),
	@Index(columnList = "createdAt"),
	@Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false)
	private String title;

	@Setter
	@Column(nullable = false, length = 10000)
	private String content;
	@Setter
	private String hashtag;

	@OrderBy("id")
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	@ToString.Exclude
	private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


	private Article(String title, String content, String hashtag) {
		this.title = title;
		this.content = content;
		this.hashtag = hashtag;
	}

	public static Article of(String title, String content, String hashtag) {
		return new Article(title, content, hashtag);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Article article))
			return false;
		return id != null && Objects.equals(id, article.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
