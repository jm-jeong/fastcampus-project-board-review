package com.fastcampus.fastcampusprojectboardreview.dto.request;

import java.io.Serializable;
import java.util.Set;

import com.fastcampus.fastcampusprojectboardreview.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboardreview.dto.HashtagDto;
import com.fastcampus.fastcampusprojectboardreview.dto.UserAccountDto;

/**
 * DTO for {@link com.fastcampus.fastcampusprojectboardreview.domain.Article}
 */
public record ArticleRequest(
    String title,
    String content
) {
  public static ArticleRequest of(String title, String content) {
    return new ArticleRequest(title, content);
  }

  public ArticleDto toDto(UserAccountDto userAccountDto) {
    return toDto(userAccountDto, null);
  }
  public ArticleDto toDto(UserAccountDto userAccountDto, Set<HashtagDto> hashtagDtos) {
    return ArticleDto.of(userAccountDto, title, content, hashtagDtos);
  }

}