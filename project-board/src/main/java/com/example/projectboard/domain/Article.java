package com.example.projectboard.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Getter // 모든 field 는 접근 가능해야한다. / @Setter 도 추가할 수 있지만, 여기서는 사용 안하는 방향으로 진행
@ToString // 쉽게 출력 가능 -> 쉬운 관찰
@Table(indexes = {
    @Index(columnList = "title"),
    @Index(columnList = "hashtag"),
    @Index(columnList = "createdAt"),
    @Index(columnList = "createdBy")
}) // index 생성 가능. @Index 어노테이션 사용해서 Indexing 가능. -> 검색기능을 위해.
@Entity
public class Article {

  // 이 도메인에 관련된 내용
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)  // AutoIncrement 를 위해. IDENTITY TYPE 으로 적용
  private Long id;

  @Setter @Column(nullable = false) private String title; // 제목  - NOT NULL / Domain 에서의 수정을 위해 @Setter 추가 / @Column(nullable = false) Not NULL 이기 때문에 추가. (기본값 True)
  @Setter @Column(nullable = false, length = 10000) private String content; // 내용  - NOT NULL / Domain 에서의 수정을 위해 @Setter 추가 / @Column(nullable = false) Not NULL 이기 때문에 추가. (기본값 True)

  @Setter private String hashtag; // 해시태그 - NULL 가능 / Domain 에서의 수정을 위해 @Setter 추가

  /* @Setter 를 클래스에 걸지않고 각 필드에 건 이유는, 사용자가 특정 필드에 접근하지 못하게 하기 위해.
     -> id 때문에 그런데, id는 내가 부여하는게 아니고, JPA Persistence Context 가 영속화를 할 때 자동으로 부여해주는 번호.
        그리고 밑의 메타데이터도 자동으로 JPA 가 만들어주기 때문에 @Setter를 열어두게 된다면, 내가 임의로 값을 수정할 수 있다. 이걸 방지하기 위해 각각의 필드에 @Setter를 붙였다.
     물론 코드 스타일마다 다르다!
  */

  // 메타데이터 / Jpa Auditing 을 사용해서 자동으로 데이터 삽입
  @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
  @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
  @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
  @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

  // @Column(nullable = false, length = 10000) -> NOT NULL 조건일때만 false 조건 삽입, length 도 추가 삽입 가능 -> 최대 길이 10000자

  // 모든 JPA Entity 는(Hibernate 구조) 기본 생성자를 가지고있어야함.
  protected Article() { // 평소에는 오픈하지 않기 때문에 protected 나 public 으로 설정. private 는 안됨. / 직접 코드 밖에서 new 로 생성하지 못하도록 protected 로 설정
  }

  private Article(String title, String content, String hashtag) { // 메타데이터를 제외한, 도메인과 관련이 있는 정보만 생성자를 통해 관리. / private 로 적용 -> factory method 를 통해 제공할 수 있게끔 함. -> new 키워드 사용안해도 됨.
    this.title = title;
    this.content = content;
    this.hashtag = hashtag;
  }

  public static Article of(String title, String content, String hashtag) { // factory method / Domain Article 을 생성할 때는 어떤걸 필요로 하는지 나타냄.
    return new Article(title, content, hashtag);
  }

  @Override // 동등성 검사
  public boolean equals(Object o) { // 엔티티를 데이터베이스에 영속화 시키고 사용하는 환경에서, 서로 다른 두 엔티티가 같은 조건이 무엇인가라는 질문에 이 equals 가 대답하는 것. 여기서의 기준은 아이디가 null 이 아니거나, id가 있다면 id가 같은지 체크해 동등성 검사 수행.
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Article article = (Article) o;
    return id != null && id.equals(article.id); // 데이터베이스를 영속화 하지 않았을 때, id = null 이기 때문에 id != null 도 체크
  }

  @Override
  public int hashCode() { // 동일성 검사의 hashCode 는 위의 로직에 따라 id 로만 해싱.
    return Objects.hash(id);
  }

  // 위의 equals 와 hashCode 메소드는 나중에 연관관계 매핑할 때, 거기서 사용하게 된다.
}
