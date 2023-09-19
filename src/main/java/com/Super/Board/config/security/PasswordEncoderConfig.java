package com.Super.Board.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  // 설정파일을 만들기 위한 어노테이션 또는 Bean을 등록하기 위한 어노테이션
public class PasswordEncoderConfig {

    // PasswordEncoder는 스프링 시큐리티의 인터페이스 객체
    // 비밀번호를 암호화하는 역할
    // BcryptPasswordEncoder는 BCrypt라는 해시 함수를 이용하여 패스워드를 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
