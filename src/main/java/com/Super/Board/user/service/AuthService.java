package com.Super.Board.user.service;

import com.Super.Board.config.security.JwtTokenProvider;
import com.Super.Board.user.dto.Login;
import com.Super.Board.user.dto.Logout;
import com.Super.Board.user.dto.MessageResponse;
import com.Super.Board.user.dto.SignUp;
import com.Super.Board.user.repository.entity.Roles;
import com.Super.Board.user.repository.RolesRepository;
import com.Super.Board.user.repository.entity.UserPrincipal;
import com.Super.Board.user.repository.UserPrincipalRepository;
import com.Super.Board.user.repository.entity.UserPrincipalRoles;
import com.Super.Board.user.repository.UserPrincipalRolesRepository;
import com.Super.Board.user.repository.entity.User;
import com.Super.Board.user.repository.UserRepository;
import com.Super.Board.exceptions.NotAcceptException;
import com.Super.Board.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserPrincipalRepository userPrincipalRepository;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final UserPrincipalRolesRepository userPrincipalRolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(transactionManager = "tmJpa")
    public boolean signUp(SignUp signUpRequest) {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
//        String username = signUpRequest.getName();

        if (userPrincipalRepository.existsByEmail(email)) {
            return false;
        }

        User userFound = userRepository.findByUserName(email)
                .orElseGet(() ->
                        userRepository.save(User.builder()
                                .userName(email)
                                .build()));

        Roles roles = rolesRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundException("ROLE_USER를 찾을 수가 없습니다."));
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .email(email)
                .user(userFound)
                .password(passwordEncoder.encode(password))
                .build();
        userPrincipalRepository.save(userPrincipal);
        userPrincipalRolesRepository.save(
                UserPrincipalRoles.builder()
                        .roles(roles)
                        .userPrincipal(userPrincipal)
                        .build()
        );
        return true;

    }

    public String login(Login loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            UserPrincipal userPrincipal = userPrincipalRepository.findByEmailFetchJoin(email)
                    .orElseThrow(() -> new NotFoundException("UserPrincipal을 찾을 수 없습니다."));

            List<String> roles = userPrincipal.getUserPrincipalRoles()
                    .stream()
                    .map(UserPrincipalRoles::getRoles)
                    .map(Roles::getName)
                    .collect(Collectors.toList());

            return jwtTokenProvider.createToken(email, roles);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotAcceptException("로그인 할 수 없습니다.");
        }

    }

    @Transactional(transactionManager = "tmJpa")
    public ResponseEntity<MessageResponse> logout(Logout logoutRequest) {
        String email = logoutRequest.getEmail();

        // 로그인된 email인지 검사
        if (!userPrincipalRepository.existsByEmail(email)) return ResponseEntity.badRequest().body(new MessageResponse("로그인된 적 없는 email입니다."));

        // signs 테이블에서 제거
        userPrincipalRepository.deleteByEmail(email);

        return ResponseEntity.ok(new MessageResponse("로그아웃되었습니다."));
    }


}