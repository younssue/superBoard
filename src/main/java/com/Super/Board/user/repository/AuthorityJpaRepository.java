package com.Super.Board.user.repository;


import com.Super.Board.user.repository.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityJpaRepository extends JpaRepository<AuthorityEntity, Integer> {

}
