package com.Super.Board.config.security;


import com.Super.Board.user.exception.JwtIsNotValidException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component  // 빈 등록시 필요
@Getter
@RequiredArgsConstructor
@Slf4j
//JwtProvider: 토큰을 생성하고 검증할 클래스를 생성
public class JwtProvider {
    // secretKey : Token 체크시 필요한 암호키
    private final SecretKey secretKey;
    //밀리세컨드 기준 : 1000L * 60는 1분. 1000L * 60 * 60는 1시간
    private final long tokenValidMilliseconds = 1000L * 60 * 60;
    private final String headerName = "Access-Token";

    // JWT 토큰 생성
    // email : user의 email
    // authorities : user의 권한을 담은 리스트
    public String createToken(String email, List<String> authorities) {
        // new Date()는 현재 날짜와 시간을 가지는 객체
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                //  claim : 페이로드라고도 불리며 Base64로 디코딩하면 JSON 형식으로 토큰의 발급자, 부여자, 유효기간, 사용자의 권한 등이
                //          담겨있음.사용자가 로그인을 한 이후에는 모든 요청들마다 사용자가 가지고 있는 토큰 정보가 서버에게 보내지게 됨
                .claim("authorities", authorities)
                // IssuedAt : 토큰이 발급된 시간
                .setIssuedAt(now)
                // Expiration : 토큰 만료되는 시간
                .setExpiration(new Date(now.getTime() + tokenValidMilliseconds))
                .signWith(secretKey)
                .compact();
    }

    // Request Header 에서 토큰 정보 추출
    // HttpServletRequest : request 객체 안에 들어간 Token 값을 가져옴
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(headerName);
    }

    // validateToken 으로 토큰 유효성 검사
    // jwt : 검사할 JWT 토큰
    public boolean validateToken(String jwt) {
        try {
            //Jwts: 위에서 JWT 생성 시 사용했던 것
            //parserBuilder(): 검증 방법
            //setSigningKey(key): Token 을 만들 때 사용한 KEY
            //parseClaimsJws(jwt): 어떤 Token 을 검증할 것인지
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);  //이 코드만으로 내부적으로 검증 가능
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 인증 객체 생성
    // Authentication 객체는 email, 인코딩된 pw, 권한 정보들을 담고 있음.
    public UsernamePasswordAuthenticationToken createAuthentication(String jwt) {
        String email = getEmail(jwt).orElseThrow(()->new JwtIsNotValidException("신뢰할 수 없는 토큰입니다."));
        List<String> authorities = getAuthorities(jwt).orElseThrow(()->new JwtIsNotValidException("신뢰할 수 없는 토큰입니다."));
        List<SimpleGrantedAuthority> GrantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(email, jwt, GrantedAuthorities);
    }

    //JWT 에서 사용자 정보 가져오기
    public Claims parseClaims(String jwt) throws JwtIsNotValidException {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
    }

    // JWT에서 user의 권한 정보를 가져옴
    private Optional<List<String>> getAuthorities(String jwt) {
        List<?> authorities = parseClaims(jwt).get("authorities", List.class);
        if (authorities == null) return Optional.empty();
        return Optional.of(authorities.stream().map(String::valueOf).collect(Collectors.toList()));
    }

    // JWT에서 email을 가져옴
    private Optional<String> getEmail(String jwt) {
        return Optional.ofNullable(parseClaims(jwt).getSubject());
    }
}
