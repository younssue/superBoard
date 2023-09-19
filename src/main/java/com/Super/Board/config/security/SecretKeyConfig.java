package com.Super.Board.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
public class SecretKeyConfig {

    // NoSuchAlgorithmException : 어느 암호 알고리즘이 요구되었음에도 불구하고, 현재의 환경에서는 사용 가능하지 않은 경우에 예외를 발생
    @Bean
    public SecretKey secretKey(@Value("super-board") String secretKeySource) throws NoSuchAlgorithmException{
        // SHA-256은 SHA(Secure Hash Algorithm) 알고리즘의 한 종류로서 256비트로 구성되며 64자리 문자열을 반환
        // 복호화가 불가능, 사용 예 : 비밀번호의 일치여부 확인
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // 패스워드 평문을 한다. '암호화'와 유사한 개념
        byte[] sha256SecretKeySource = digest.digest(secretKeySource.getBytes(StandardCharsets.UTF_8));
        // Base64 : Binary Data를 Text로 바꾸는 Encoding ( 공통 ASCII 영역의 문자로만 이루어진 문자열로 바꾸는 Encoding )
        // MAC : 메시지의 인증에 쓰이는 코드, 메시지의 무결성 및 신뢰성을 보장하는 용도로 사용
        // HMAC은 인증을 위한 Secret Key와 임의의 길이를 가진 Message를 해시 함수를 사용해서 생성
        // 알고리즘에 따라 다른 고정길이의 MAC(Hash value)가 생성
        // HMAC는 메시지를 암호화하지 않는 대신 메시지의 암호화 여부에 관계 없이 메시지는 HMAC 해시와 함께 송신
        return new SecretKeySpec(Base64.getEncoder().encode(sha256SecretKeySource), "HmacSHA256");
    }
}
