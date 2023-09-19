package com.Super.Board.user.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(String.format("%s %s 요청이 들어왔습니다.", request.getMethod(), request.getRequestURI()));

        filterChain.doFilter(request, response);

        log.info(String.format("%s %s 응답 상태 코드: %s", request.getMethod(), request.getRequestURI(), response.getStatus()));
    }
}
