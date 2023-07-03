package com.example.projectboard.domain;

import java.time.LocalDateTime;

public class ArticleComment {

  // 이 도메인에 관련된 내용
  private Long id;
  private Article article; // 게시글 (ID)
  private String content; // 본문

  // 메타데이터
  private LocalDateTime createdAt; // 생성일시
  private String createdBy; // 생성자
  private LocalDateTime modifiedAt; // 수정일시
  private String modifiedBy; // 수정자

}
