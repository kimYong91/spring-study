package com.busanit.spring_study.buva.noticeBoard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 만드는 순서 15
// 스프링 시큐리티 클래스
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/question/**", "/bootstrap.min.css", "/js/bootstrap.min.js", "answer/**", "/h2-console/**").permitAll()
                                                        // 여기에 입력한 주소만 로그인 없이 들어갈 수 있다.
                                                        // css, js의 주소도 포함 시켜야 디자인이 적용된 상태로 나옴
                                                        // question/**, answer/**는 question, answer 안에 있는 주소는 전부 허용한다는 의미
        ).csrf(
                (csrf) -> csrf.ignoringRequestMatchers(
                        new AntPathRequestMatcher("/h2-console/**")
                                                        // 토큰 없이도 들어갈수 있는 주소라는 의미 *사용시 주의, 사용시 꼭 이유가 필요함
                )
        ).headers(  // csrf 와 세트라고 보면 됨 * 똑같이 사용시 주의
                (headers) ->
                        headers.addHeaderWriter(
                                new XFrameOptionsHeaderWriter(
                                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
                                )
                        )
        );
        return http.build();
    }
}
