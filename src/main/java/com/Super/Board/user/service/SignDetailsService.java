package com.Super.Board.user.service;


import com.Super.Board.user.exception.NotFoundException;
import com.Super.Board.user.repository.SignJpaRepository;
import com.Super.Board.user.repository.details.SignDetails;
import com.Super.Board.user.repository.entity.AuthorityEntity;
import com.Super.Board.user.repository.entity.SignEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignDetailsService implements UserDetailsService {

    private final SignJpaRepository signJpaRepository;

    /**
     * 메서드의 이름은 Username이 들어있지만 실제 email을 이용해 SignDetails 객체를 가져옵니다.
     *
     * @param email 유저의 email
     * @return SignDetails 객체
     */
    @Override
    public SignDetails loadUserByUsername(String email) {
        SignEntity sign = signJpaRepository.findByEmail(email).orElseThrow(()->new NotFoundException("존재하지 않는 email입니다."));
        List<String> authorities = sign.getUser().getAuthorities().stream().map(AuthorityEntity::getAuthority).collect(Collectors.toList());

        return SignDetails.builder()
                .email(sign.getEmail())
                .token(sign.getToken())
                .authorities(authorities)
                .build();
    }
}
