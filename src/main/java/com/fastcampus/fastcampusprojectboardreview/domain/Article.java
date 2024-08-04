package com.fastcampus.fastcampusprojectboardreview.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
	@Index(columnList = "title"),
	@Index(columnList = "hashtag"),
	@Index(columnList = "createdAt"),
	@Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
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

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;
	@CreatedBy
	@Column(nullable = false)
	private String createdBy;
	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime modifiedAt;
	@LastModifiedBy
	@Column(nullable = false)
	private String modifiedBy;


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
