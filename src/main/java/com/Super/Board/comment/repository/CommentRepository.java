package com.Super.Board.comment.repository;

import com.Super.Board.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<CommentEntity, Long > {

}
