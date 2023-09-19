package com.Super.Board.user.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of="id")
@ToString
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;

    @Column(name="email", length = 20 ,unique = true, nullable = false)
    private String email;

    @Column(name="password", length = 100, nullable = false)
    private String password;

    @Column(name="username", length = 20, nullable = false)
    private String name;

    @OneToMany
    private List<AuthorityEntity> authorities;
}
