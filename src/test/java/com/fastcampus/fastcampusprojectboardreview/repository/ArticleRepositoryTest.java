package com.fastcampus.fastcampusprojectboardreview.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.fastcampus.fastcampusprojectboardreview.config.JpaConfig;
import com.fastcampus.fastcampusprojectboardreview.domain.Article;
import com.fastcampus.fastcampusprojectboardreview.domain.UserAccount;

@Import(JpaConfig.class)
@DataJpaTest
class ArticleRepositoryTest {

	@Autowired
	private ArticleRepository articleRepository;

	@Test
	void testSaveAndFindById() {
		// Given
		UserAccount userAccount = UserAccount.of("userid", "password", "user@mail.com", "nickname", "memo");
		Article article = Article.of(userAccount, "title", "content", "hashtag");
		article.setTitle("Spring Boot 3");
		article.setContent("New features in Spring Boot 3");
		article.setHashtag("#springboot");

		// When
		Article savedArticle = articleRepository.save(article);

		// Then
		Optional<Article> foundArticle = articleRepository.findById(savedArticle.getId());
		assertThat(foundArticle).isPresent();
		assertThat(foundArticle.get().getTitle()).isEqualTo("Spring Boot 3");
	}

	@Test
	void testUpdateArticle() {
		// Given
		UserAccount userAccount = UserAccount.of("userid", "password", "user@mail.com", "nickname", "memo");
		Article article = Article.of(userAccount, "title", "content", "hashtag");
		article.setTitle("Initial Title");
		article.setContent("Initial Content");
		article.setHashtag("#initial");

		Article savedArticle = articleRepository.save(article);

		// When
		savedArticle.setTitle("Updated Title");
		Article updatedArticle = articleRepository.save(savedArticle);

		// Then
		Optional<Article> foundArticle = articleRepository.findById(updatedArticle.getId());
		assertThat(foundArticle).isPresent();
		assertThat(foundArticle.get().getTitle()).isEqualTo("Updated Title");
	}

	@Test
	void testDeleteArticle() {
		// Given
		UserAccount userAccount = UserAccount.of("userid", "password", "user@mail.com", "nickname", "memo");
		Article article = Article.of(userAccount, "title", "content", "hashtag");
		article.setTitle("To Be Deleted");
		article.setContent("This article will be deleted");
		article.setHashtag("#delete");

		Article savedArticle = articleRepository.save(article);

		// When
		articleRepository.delete(savedArticle);

		// Then
		Optional<Article> foundArticle = articleRepository.findById(savedArticle.getId());
		assertThat(foundArticle).isNotPresent();
	}
}