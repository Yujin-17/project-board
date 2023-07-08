package com.example.projectboard.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter // 모든 field 는 접근 가능해야한다. / @Setter 도 추가할 수 있지만, 여기서는 사용 안하는 방향으로 진행
@ToString // 쉽게 출력 가능 -> 쉬운 관찰
@Table(indexes = {
    @Index(columnList = "content"),
    @Index(columnList = "createdAt"),
    @Index(columnList = "createdBy")
}) // index 생성 가능. @Index 어노테이션 사용해서 Indexing 가능. -> 검색기능을 위해.
@EntityListeners(AuditingEntityListener.class) // 이게 있어야 Auditing 동작
@Entity
public class ArticleComment {

  // 이 도메인에 관련된 내용
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)  // AutoIncrement 를 위해. IDENTITY TYPE 으로 적용
  private Long id;
  @Setter @ManyToOne(optional = false) private Article article; // 게시글 (ID) // 연관관계 메핑을 위해 @ManyToOne 어노테이션, optional = false 로 필수값 설정, cascade 이 옵션도 줄 수 있는데, 댓글을 변경 삭제 시 관련 게시글이 영향을 받는지 설정 -> 댓글은 혼자 지워져야함. 때문에 기본값인 none 사용
  @Setter @Column(nullable = false, length = 500) private String content; // 본문

  // 메타데이터
  @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
  @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
  @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
  @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

  protected ArticleComment() {
  }

  private ArticleComment(Article article, String content) {
    this.article = article;
    this.content = content;
  }

  public static ArticleComment of(Article article, String content){ // 위를 private 로 막아줬기 때문에 factory method 로 open
    return new ArticleComment(article, content);
  }
  /* 기본 생성자를 만들지 않고 아래 롬복을 사용해서 할 수 있지만, 짧고 롬복 사용을 줄이기 위해 위의 생성자 생성.
     @NoArgsConstructor(access = AccessLevel.PROTECTED) -> access 도 설정 가능. (protected)
  */

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArticleComment that = (ArticleComment) o;
    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
