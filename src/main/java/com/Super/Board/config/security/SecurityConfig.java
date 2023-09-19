package com.Super.Board.config.security;


import com.Super.Board.user.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티를 활성화하고 웹 보안 설정을 구성
@RequiredArgsConstructor    // Lombok으로 생성자 주입을 임의의 코드없이 자동으로 설정해주는 어노테이션
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .headers()
                    .frameOptions()
                    // sameOrigin은 같은 도메인 내에서의 참조만 허용
                    .sameOrigin()
                .and()
                // form을 통한 Login을 비활성화
                .formLogin().disable()
                // csrf 토큰 검사를 비활성화
                .csrf().disable()
                // 기본 인증 로그인 비활성화
                .httpBasic().disable()
                // rememberMe 기능 비활성화
                .rememberMe().disable()
                // 스프링 시큐리티가 세션을 생성하지도 않고 존재해도 사용하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 접근 권한 설정
                .authorizeRequests()
                    // permitAll : 지정된 경로 모두 접근 허용
                    .antMatchers("/resources/static/**", "/api/signup", "/api/login", "/api/logout").permitAll()
                // 그외 나머지 리소스들은 무조건 인증을 완료해야 접근 가능
                .anyRequest().authenticated()
                .and()
                // UsernamePasswordAuthenticationFilter : 인증을 처리하는 기본 필터
                // addFilterBefore : 커스텀 필터를 UsernamePasswordAuthenticationFilter보다 먼저 실행
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
