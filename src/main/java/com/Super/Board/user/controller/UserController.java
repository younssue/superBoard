package com.Super.Board.user.controller;

import com.Super.Board.user.dto.LogoutRequest;
import com.Super.Board.user.dto.MessageResponse;
import com.Super.Board.user.dto.SignRequest;
import com.Super.Board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signUp(@RequestBody SignRequest signRequest) {
        return userService.signUp(signRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody SignRequest signRequest, HttpServletResponse response) {
        return userService.login(signRequest, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestBody LogoutRequest logoutRequest) {
        return userService.logout(logoutRequest);
    }
}
