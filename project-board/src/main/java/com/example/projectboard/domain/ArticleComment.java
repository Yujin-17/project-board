package com.example.projectboard.domain;

import java.time.LocalDateTime;

public class ArticleComment {

  // 이 도메인에 관련된 내용
  private Long id;
  private Article article;
  private String content;

  // 메타데이터
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime modifiedAt;
  private String modifiedBy;

}
