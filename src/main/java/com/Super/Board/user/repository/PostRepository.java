package com.Super.Board.user.repository;

import com.Super.Board.user.repository.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity,Long> {

    List<PostEntity> findByAuthorContaining(String author);
}
