package com.Super.Board.user.controller;

import com.Super.Board.user.dto.Login;
import com.Super.Board.user.dto.Logout;
import com.Super.Board.user.dto.MessageResponse;
import com.Super.Board.user.dto.SignUp;
import com.Super.Board.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = "/api")
public class SignController {

    private final AuthService authService;

    @PostMapping(value = "/signup")
    public String signup(@RequestBody SignUp signUpRequest){
        boolean isSuccess = authService.signUp(signUpRequest);
        return isSuccess ? "회원가입 성공하였습니다." : "회원가입 실패하였습니다.";
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse){
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);
        return "로그인이 성공하였습니다.";
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestBody Logout logoutRequest) {
        return authService.logout(logoutRequest);
    }


}