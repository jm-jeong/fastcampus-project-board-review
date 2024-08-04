package com.fastcampus.fastcampusprojectboardreview.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.fastcampus.fastcampusprojectboardreview.config.JpaConfig;
import com.fastcampus.fastcampusprojectboardreview.domain.Article;

@ActiveProfiles("testdb")
@DisplayName("JPA 연결 테스트")
@Import({JpaConfig.class})
@DataJpaTest
public class JpaRepositoryTest {
	private final ArticleRepository articleRepository;
	private final ArticleCommentRepository articleCommentRepository;

	public JpaRepositoryTest(
		@Autowired ArticleRepository articleRepository,
		@Autowired ArticleCommentRepository articleCommentRepository
	) {
		this.articleRepository = articleRepository;
		this.articleCommentRepository = articleCommentRepository;
	}

	@DisplayName("select Test")
	@Test
	void givenTestData_whenSelecting_thenSuccess() {
		//Given


		//When
		List<Article> articles = articleRepository.findAll();

		//Then
		assertThat(articles).isNotNull().hasSize(123);//Test데이터 사이즈
	}

	@DisplayName("insert Test")
	@Test
	void givenTestData_whenInserting_thenSuccess() {
		//Given
		Article article = Article.of("제목", "내용", "해시태그");
		long previousCount = articleRepository.count();

		//When
		articleRepository.save(article);

		//Then
		assertThat(articleRepository.count()).isEqualTo(previousCount + 1	);
	}

	@DisplayName("update Test")
	@Test
	void givenTestData_whenUpdating_thenSuccess() {
		//Given
		Article article = articleRepository.findById(1L).orElseThrow();
		String updateHashtag = "newHashtag";
		article.setHashtag(updateHashtag);

		//When
		Article updatedArticle = articleRepository.saveAndFlush(article);

		//Then
		assertThat(updatedArticle.getHashtag()).isEqualTo(updateHashtag);
		assertThat(updatedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
	}

	@DisplayName("delete Test")
	@Test
	void givenTestData_whenDeleting_thenSuccess() {
		//Given
		Article article = articleRepository.findById(1L).orElseThrow();
		long previousArticleCount = articleRepository.count();
		long previousArticleCommentCount = articleCommentRepository.count();
		int deletedCommentsSize = article.getArticleComments().size();

		//When
		articleRepository.delete(article);

		//Then
		assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
		assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
	}
}
