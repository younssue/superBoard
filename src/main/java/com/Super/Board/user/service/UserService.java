package com.Super.Board.user.service;


import com.Super.Board.config.security.JwtProvider;
import com.Super.Board.user.dto.LogoutRequest;
import com.Super.Board.user.dto.MessageResponse;
import com.Super.Board.user.dto.SignRequest;
import com.Super.Board.user.exception.BadRequestException;
import com.Super.Board.user.exception.ConflictException;
import com.Super.Board.user.exception.NotFoundException;
import com.Super.Board.user.repository.AuthorityJpaRepository;
import com.Super.Board.user.repository.SignJpaRepository;
import com.Super.Board.user.repository.UserJpaRepository;
import com.Super.Board.user.repository.entity.AuthorityEntity;
import com.Super.Board.user.repository.entity.AuthorityType;
import com.Super.Board.user.repository.entity.SignEntity;
import com.Super.Board.user.repository.entity.UserEntity;
import com.Super.Board.user.util.JpaManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final SignJpaRepository signJpaRepository;
    private final AuthorityJpaRepository authorityJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /**
     * 유저 회원가입을 진행합니다.
     *
     * @param signRequest email, password, username을 담고 있는 SignRequest 객체입니다.
     *                    email, password, username은 null이 될 수 없습니다.
     * @return 200 OK: 회원가입에 성공하면 "회원가입이 완료되었습니다" 메세지가 담긴 MessageResponse 객체를 반환합니다.
     */
    @Transactional(transactionManager = "tmJpa")
    public ResponseEntity<MessageResponse> signUp(SignRequest signRequest) {
        String email = signRequest.getEmail();
        String password = signRequest.getPassword();
        String name = signRequest.getName();

        // email, password, name 누락됐는지 검사
        if (email == null || password == null || name == null) throw new BadRequestException("email 혹은 password가 누락되었습니다.");

        // 이미 회원가입된 email이면 409 Conflict
        if (userJpaRepository.existsByEmail(email)) throw new ConflictException("이미 가입된 email입니다.");

        // pw 인코딩
        String encodedPassword = encode(password);

        // user 저장
        UserEntity user = UserEntity.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .build();
        JpaManager.managedSave(userJpaRepository, user);

        // 기본적으로 회원가입하면 모두 USER 권한 획득
        AuthorityEntity authority = AuthorityEntity.builder()
                .authority(AuthorityType.USER.name())
                .user(user)
                .build();
        JpaManager.managedSave(authorityJpaRepository, authority);

        return ResponseEntity.ok(new MessageResponse("회원가입이 완료되었습니다."));
    }

    /**
     * 유저 로그인을 진행합니다.
     *
     * @param signRequest 로그인 하는 유저의 email, password를 담고 있는 SignRequest 객체입니다.
     *                    email, password는 null이 될 수 없습니다.
     * @return 200 OK: "로그인이 성공적으로 완료되었습니다" 메세지가 담긴 MessageResponse 객체를 반환합니다.
     */
    public ResponseEntity<MessageResponse> login(SignRequest signRequest, HttpServletResponse response) {
        String email = signRequest.getEmail();
        String password = signRequest.getPassword();

        // email, pw가 누락되었는지 검사
        if (email == null || password == null) throw new BadRequestException("email 혹은 password가 누락되었습니다.");

        // email로 user 검색
        UserEntity user = userJpaRepository.findByEmail(email).orElseThrow(()->new NotFoundException("존재하지 않는 email입니다."));

        // 이미 로그인 되어 있는지 검사
        if (signJpaRepository.existsByEmail(email)) throw new ConflictException("이미 로그인된 email입니다.");

        // pw가 올바른지 검사
        String encodedPassword = user.getPassword();
        if (!passwordEncoder.matches(password, encodedPassword)) throw new BadRequestException("password가 옳지 않습니다.");

        // 권한 획득
        List<String> authorities = user.getAuthorities().stream().map(AuthorityEntity::getAuthority).collect(Collectors.toList());

        // JWT 토큰 생성
        String jwtToken = jwtProvider.createToken(email, authorities);

        // 로그인 정보를 저장하는 sign 객체
        // 로그인 하면 signs 테이블에 저장, 로그아웃하면 제거
        SignEntity sign = SignEntity.builder()
                .email(email)
                .token(jwtToken)
                .user(user)
                .build();
        JpaManager.managedSave(signJpaRepository, sign);

        // 리스폰스 헤더에 붙여서 클라이언트로 토큰 전달
        response.setHeader(jwtProvider.getHeaderName(), jwtToken);
        return ResponseEntity.ok(new MessageResponse("로그인이 성공적으로 완료되었습니다."));
    }

    /**
     * 유저의 로그아웃을 진행합니다.
     *
     * @param logoutRequest email이 담긴 logoutRequest 객체입니다.
     *                      email은 null이 될 수 없습니다.
     * @return 200 OK: "로그아웃되었습니다." 메세지가 담긴 MessageResponse 객체를 반환합니다.
     */
    @Transactional(transactionManager = "tmJpa")
    public ResponseEntity<MessageResponse> logout(LogoutRequest logoutRequest) {
        String email = logoutRequest.getEmail();

        // 로그인된 email인지 검사
        if (!signJpaRepository.existsByEmail(email)) return ResponseEntity.badRequest().body(new MessageResponse("로그인된 적 없는 email입니다."));

        // signs 테이블에서 제거
        signJpaRepository.deleteByEmail(email);
        return ResponseEntity.ok(new MessageResponse("로그아웃되었습니다."));
    }

//    /**
//     * user의 권한들을 가져옵니다.
//     *
//     * @param userEntity 권한 정보를 가져올 user
//     * @return 권한 정보들을 담은 리스트
//     */
//    private List<String> getAuthorities(UserEntity userEntity) {
//        return List.of(userEntity.getAuthority().name());
//    }

    /**
     * 주어진 비밀번호를 인코딩하여 반환합니다.
     * @param password 인코딩할 비밀번호
     * @return 인코딩된 비밀번호
     */
    private String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
