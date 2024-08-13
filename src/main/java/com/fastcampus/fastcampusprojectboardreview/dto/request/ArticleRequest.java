package com.fastcampus.fastcampusprojectboardreview.dto.request;

import java.io.Serializable;

import com.fastcampus.fastcampusprojectboardreview.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboardreview.dto.UserAccountDto;

/**
 * DTO for {@link com.fastcampus.fastcampusprojectboardreview.domain.Article}
 */
public record ArticleRequest(
    String title,
    String content,
    String hashtag
) {
  public static ArticleRequest of(String title, String content, String hashtag) {
    return new ArticleRequest(title, content, hashtag);
  }

  public ArticleDto toDto(UserAccountDto dto) {
    return ArticleDto.of(dto, title, content, hashtag);
  }
}