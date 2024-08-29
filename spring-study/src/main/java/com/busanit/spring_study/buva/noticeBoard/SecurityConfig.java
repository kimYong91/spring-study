package com.busanit.spring_study.buva.noticeBoard;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

// 만드는 순서
// 스프링 시큐리티 클래스
// 자신의 서버에서 다운을 받는지, 보는지 등을 확인하는 기능이 있어서 비회원 홈페이지를 하더라도 필요함
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/**", "/question/**", "/bootstrap.min.css", "/js/bootstrap.min.js",
                                        "answer/**", "/h2-console/**", "/user/**").permitAll()
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
        ).formLogin(
                // 로그인
                (formLogin) -> formLogin.loginPage("/user/login").defaultSuccessUrl("/")
                                                                        // 로그인에 성공하면 '/'주소로 간다.
        ).logout(
                // 로그아웃 : 따로 입력할 것이 없어서 이 코드 하나로 로그아웃이 해결됨
                (logout) -> logout.logoutRequestMatcher(
                        new AntPathRequestMatcher("/user/logout")
                ).logoutSuccessUrl("/").invalidateHttpSession(true)
                // 로그아웃에 성공하면 '/'주소로 간다.
        );
        return http.build();
    }

    // 정적 리소스를 spring security 대상에서 제외 설정
    // csrf: 토근 없이 해도 된다는 것을 추가 하는것이고, WebSecurityCustomizer: 토큰이 필요없다고 완전히 제외 시키는 것이다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // 로그인 관련 메서드
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
