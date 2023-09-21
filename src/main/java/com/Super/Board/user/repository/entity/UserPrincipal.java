package com.Super.Board.user.repository.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "user_principal")
public class UserPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_principal_id")
    private Long userPrincipalId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", unique = true)
    private String password;

    @OneToMany(mappedBy = "userPrincipal", orphanRemoval = true)
    private Collection<UserPrincipalRoles> userPrincipalRoles;
}
