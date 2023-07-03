package com.example.projectboard.domain;

import java.time.LocalDateTime;

public class Article {

  // 이 도메인에 관련된 내용
  private Long id;
  private String title; // 제목
  private String content; // 내용
  private String hashtag; // 해시태그

  // 메타데이터
  private LocalDateTime createdAt; // 생성일시
  private String createdBy; // 생성자
  private LocalDateTime modifiedAt; // 수정일시
  private String modifiedBy; // 수정자

}
