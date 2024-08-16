package com.fastcampus.fastcampusprojectboardreview.domain;

import java.util.Collection;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

	@ToString.Exclude
	@JoinTable(
		name = "article_hashtag",
		joinColumns = @JoinColumn(name = "articleId"),
		inverseJoinColumns = @JoinColumn(name = "hashtagId")
	)
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Hashtag> hashtags = new LinkedHashSet<>();

	@Setter @ManyToOne(optional = false)
	@JoinColumn(name = "userId")
	private UserAccount userAccount; // 유저 정보 (ID)

	@OrderBy("createdAt desc")
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	@ToString.Exclude
	private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


	private Article(UserAccount userAccount, String title, String content) {
		this.userAccount = userAccount;
		this.title = title;
		this.content = content;
	}

	public static Article of(UserAccount userAccount, String title, String content) {
		return new Article(userAccount, title, content);
	}

	public void addHashtag(Hashtag hashtag) {
		this.getHashtags().add(hashtag);
	}

	public void addHashtags(Collection<Hashtag> hashtags) {
		this.getHashtags().addAll(hashtags);
	}

	public void clearHashtags() {
		this.getHashtags().clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Article that))
			return false;
		return this.getId() != null && Objects.equals(this.getId(), that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getId());
	}
}
