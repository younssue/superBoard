package com.Super.Board.user.repository;

import com.Super.Board.user.repository.entity.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPrincipalRepository  extends JpaRepository<UserPrincipal, Long> {

    @Query("SELECT up FROM UserPrincipal up JOIN FETCH up.userPrincipalRoles upr JOIN FETCH upr.roles WHERE up.email = :email ")
    Optional<UserPrincipal> findByEmailFetchJoin(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);


}
