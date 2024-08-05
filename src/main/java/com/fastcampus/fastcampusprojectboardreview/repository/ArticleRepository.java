package com.fastcampus.fastcampusprojectboardreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fastcampus.fastcampusprojectboardreview.domain.Article;
import com.fastcampus.fastcampusprojectboardreview.domain.QArticle;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long>,
	QuerydslPredicateExecutor<Article>,//모든 필드에 대하여 검색 가능 exact match
	QuerydslBinderCustomizer<QArticle>//필드 별로 커스터마이징 기능 제공, 모든 필드가 검색될 필요도 exact match도 필요한게 아니라 해당부분 선언하여 커스터마이징
{
	@Override
	default void customize(QuerydslBindings bindings, QArticle root) {
		bindings.excludeUnlistedProperties(true);// 모든 필드 검색 안되게
		bindings.including(root.title, root.content, root.createdBy, root.createdAt, root.hashtag);//검색 기능 제공할 필드만
		bindings.bind(root.title).first(StringExpression::containsIgnoreCase);// like '%${v}%'
		// bindings.bind(root.title).first(StringExpression::likeIgnoreCase);// like '${v}'
		bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
		bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
		bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
		bindings.bind(root.createdAt).first(DateTimeExpression::eq);

	}
}
