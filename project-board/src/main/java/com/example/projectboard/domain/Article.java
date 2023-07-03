package com.example.projectboard.domain;

import java.time.LocalDateTime;

public class Article {

  // 이 도메인에 관련된 내용
  private Long id;
  private String title;
  private String content;
  private String hashtag;

  // 메타데이터
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime modifiedAt;
  private String modifiedBy;

}
