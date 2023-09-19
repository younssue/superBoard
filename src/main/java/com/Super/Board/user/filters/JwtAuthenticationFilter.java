package com.Super.Board.user.filters;


import com.Super.Board.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT 토큰을 꺼내옵니다.
        String jwt = jwtProvider.resolveToken(request);

        // JWT 토큰이 유효하다면
        if (jwtProvider.validateToken(jwt)) {
            // Authentication 객체를 생성하여 Security Context에 저장
            Authentication auth = jwtProvider.createAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 다음 필터 실행
        filterChain.doFilter(request, response);
    }
}
