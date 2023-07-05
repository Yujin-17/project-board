package com.example.projectboard.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JpaAuditing 기능 활성화
@Configuration // JpaConfig 클래스를 Configuration Bean 으로 설정
public class JpaConfig {

  @Bean
  public AuditorAware<String> auditorAware() { // Jpa Auditing 을 할 때, 아직 생성자에 들어갈 이름이 없으면(인증기능이나 security 를 구현하지 않은 단계) 이 메소드를 생성해
    return() -> Optional.of("yujin");  // 항상 yujin 이라는 이름으로 값을 삽입. -> 일종의 임의데이터 // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때, 수정
  }  // 인증기능을 붙이게 된다면, 누가 데이터를 넣었는지 식별 가능.

}
