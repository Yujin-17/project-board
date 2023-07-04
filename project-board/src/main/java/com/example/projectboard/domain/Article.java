package com.example.projectboard.domain;

import java.time.LocalDateTime;
import javax.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Getter // 모든 field 는 접근 가능해야한다. / @Setter 도 추가할 수 있지만, 여기서는 사용 안하는 방향으로 함.
@ToString // 쉽게 출력 가능 -> 쉬운 관찰
@Table(indexs = ) // index 생성 가능.
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
