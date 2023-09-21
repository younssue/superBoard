package com.Super.Board.post.repository;

import com.Super.Board.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity,Long> {

    List<PostEntity> findByAuthorContaining(String author);
}
