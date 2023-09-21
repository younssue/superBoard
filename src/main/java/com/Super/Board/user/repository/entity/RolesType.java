package com.Super.Board.user.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RolesType {
    USER("유저"),
    ADMIN("관리자");

    private final String key;
}
