package com.fastcampus.fastcampusprojectboardreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fastcampus.fastcampusprojectboardreview.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboardreview.domain.QArticle;
import com.fastcampus.fastcampusprojectboardreview.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;

@RepositoryRestResource
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long>,
	QuerydslPredicateExecutor<ArticleComment>,
	QuerydslBinderCustomizer<QArticleComment>
{

	@Override
	default void customize(QuerydslBindings bindings, QArticleComment root) {
		bindings.excludeUnlistedProperties(true);// 모든 필드 검색 안되게
		bindings.including(root.content, root.createdBy, root.createdAt);//검색 기능 제공할 필드만
		bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
		bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
		bindings.bind(root.createdAt).first(DateTimeExpression::eq);

	}
}
