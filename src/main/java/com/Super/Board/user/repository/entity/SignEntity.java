package com.Super.Board.user.repository.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of="id")
@Table(name="signs")
public class SignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sign_id")
    private int id;

    @Column(name="email", length = 20, unique = true, nullable = false)
    private String email;

    @Column(name="token", length = 300, unique = true, nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
}
