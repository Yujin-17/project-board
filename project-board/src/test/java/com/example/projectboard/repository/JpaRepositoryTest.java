package com.example.projectboard.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.projectboard.config.JpaConfig;
import com.example.projectboard.domain.Article;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // test 클래스에는 JpaConfig가 있는지 확인을 못하기 때문에 import를 해줘야함.
@DataJpaTest // 슬라이스 테스트 / 모든 데이터베이스 설정을 무시하고, 테스트가 자기가 정해놓은 DB를 띄움.  -> 이걸 막기위해 @AutoConfigureTestDatabase(replace = Replace.NONE) 이 어노테이션 붙여주면 됨. 이걸 사용하면, 테스트 상태에서 돌려도 테스트 디비를 불러오지 않고 설정된걸 쓴다. -> 내가 설정한 mysql 환경으로 테스트도 가능하다는 뜻.
class JpaRepositoryTest {

  @Autowired private ArticleRepository articleRepository; // @Autowired 를 사용해 필드를 주입.
  private final ArticleCommentRepository articleCommentRepository; // 생성자 주입 패턴으로도 테스트 만들 수 있다.

  public JpaRepositoryTest(
      @Autowired ArticleCommentRepository articleCommentRepository) { // 생성자 주입 패턴은 @Autowired 를 여기다가 삽입
    this.articleCommentRepository = articleCommentRepository;
  }

  /*
    @Autowired 를 사용해 필드를 주입하는 방식은, 스프링 프레임워크에서 사용가능하지만, 몇가지 문제 발생.
      -> 필드 주입은 테스트 하기 어려우며, 의존성 주입이 명시적으로 표현되지 않아 코드의 가독성 떨어짐. 또한, 주입할 의존성을 변경 시 해당 클래스의 소스 코드를 수정해야함.
    반면 생성자 주입은, 클래스의 생성자를 통해 의존성을 주입받아, 코드의 가독성이 좋아지고, 테스트 하기 용이. 의존성을 변경하려면 해당 클래스의 생성자만 수정하면 되어 유연성과 확장성 증가.
    => 생성자 주입 패턴 권장.
   */

  // 간단한 CRUD Test
  @DisplayName("select 테스트")
  @Test
  void givenTestData_whenSelecting_thenWorksFine() {
    // given

    // when
    List<Article> articles = articleRepository.findAll();

    // then
    assertThat(articles)
        .isNotNull()
        .hasSize(0);
  }

  @DisplayName("insert 테스트")
  @Test
  void givenTestData_whenInserting_thenWorksFine() { // 측정방식 : 기존 카운트에서 + 1 한 값이면 True
    // given
    long previousCount = articleRepository.count();

    // when
    Article saveArticle = articleRepository.save(Article.of("new article", "new content", "#spring"));

    // then
    assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
  }

  @DisplayName("update 테스트")
  @Test
  void givenTestData_whenUpdating_thenWorksFine() { // 측정방식 : 기존 카운트에서 + 1 한 값이면 True
    // given
    // Article article = articleRepository.findById(1L).orElseThrow(); // 원래 밑에 줄 빼고 이것만 해도 됨.
    Article article = articleRepository.save(Article.of("new article", "new content", "#spring")); // data 가 아직 없어서 임의로 하나 추가.
    articleRepository.findById(1L).orElseThrow();
    String updateHashtag = "#springboot";
    article.setHashtag(updateHashtag);

    // when
    Article savedArticle = articleRepository.saveAndFlush(article);

    // then
    assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
  }

  @DisplayName("delete 테스트")
  @Test
  void givenTestData_whenDeleting_thenWorksFine() { // 측정방식 : 기존 카운트에서 + 1 한 값이면 True
    // given
    // Article article = articleRepository.findById(1L).orElseThrow(); // 원래 밑에 줄 빼고 이것만 해도 됨.
    Article article = articleRepository.save(Article.of("new article", "new content", "#spring")); // data 가 아직 없어서 임의로 하나 추가.
    articleRepository.findById(1L).orElseThrow();
    long previousArticleCount = articleRepository.count(); // count 로 delete 되었는지 확인.
    long previousArticleCommentCount = articleCommentRepository.count(); // 연관관계 매핑 또한 삭제되는지 확인(전체 댓글 갯수)
    int deletedCommentsSize = article.getArticleComments().size(); // 미리 지워질 댓글의 사이즈를 확인

    // when
    articleRepository.delete(article); // void method 라 리턴값 x

    // then
    assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
    assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
  }
}