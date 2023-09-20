package com.Super.Board.user.service.security;

import com.Super.Board.user.repository.entity.Roles;
import com.Super.Board.user.repository.userDetails.CustomUserDetails;
import com.Super.Board.user.repository.entity.UserPrincipal;
import com.Super.Board.user.repository.UserPrincipalRepository;
import com.Super.Board.user.repository.entity.UserPrincipalRoles;
import com.Super.Board.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserPrincipalRepository userPrincipalRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserPrincipal userPrincipal = userPrincipalRepository.findByEmailFetchJoin(email)
                .orElseThrow(() -> new NotFoundException("email에 해당하는 UserPrincipal가 없습니다"));

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(Math.toIntExact(userPrincipal.getUser()
                        .getUserId()))
                .email(userPrincipal.getEmail())
                .password(userPrincipal.getPassword())
                .authorities(userPrincipal.getUserPrincipalRoles()
                        .stream()
                        .map(UserPrincipalRoles::getRoles)
                        .map(Roles::getName)
                        .collect(Collectors.toList()))
                .build();
        return customUserDetails;
    }
}