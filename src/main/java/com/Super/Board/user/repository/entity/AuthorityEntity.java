package com.Super.Board.user.repository.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="authorities")
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="authority_id")
    private int authorityId;

    @Column(name="authority", length = 20, nullable = false)
    private String authority;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private UserEntity user;

}
