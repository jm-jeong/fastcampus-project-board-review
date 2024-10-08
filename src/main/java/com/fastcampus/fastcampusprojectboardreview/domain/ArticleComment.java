package com.fastcampus.fastcampusprojectboardreview.domain;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
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
	@ManyToOne(optional = false)
	private Article article;

	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(name = "userId")
	private UserAccount userAccount; // 유저 정보 (ID)

	@Setter
	@Column(updatable = false)
	private Long parentCommentId;

	@ToString.Exclude
	@OrderBy("createdAt")
	@OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
	private Set<ArticleComment> childComments = new LinkedHashSet<>();

	@Setter
	@Column(nullable = false, length = 500)
	private String content;


	private ArticleComment(Article article, UserAccount userAccount, Long parentCommentId, String content) {
		this.article = article;
		this.userAccount = userAccount;
		this.parentCommentId = parentCommentId;
		this.content = content;
	}

	public static ArticleComment of(Article article, UserAccount userAccount, String content) {
		return new ArticleComment(article, userAccount, null, content);//TODO parentCommentId 세팅해야함
	}

	public void addChildComment(ArticleComment child) {
		child.setParentCommentId(child.getId());
		this.getChildComments().add(child);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ArticleComment that))
			return false;
		return this.getId() != null && Objects.equals(this.getId(), that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getId());
	}
}
