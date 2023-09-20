package com.Super.Board.post.repository;

import com.Super.Board.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity,Long> {

    List<PostEntity> findByPostWriterContaining(String author);
}
