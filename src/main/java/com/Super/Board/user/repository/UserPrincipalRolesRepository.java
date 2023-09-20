package com.Super.Board.user.repository;

import com.Super.Board.user.repository.entity.UserPrincipalRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrincipalRolesRepository extends JpaRepository<UserPrincipalRoles, Long> {

}
