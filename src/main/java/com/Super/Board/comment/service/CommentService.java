package com.Super.Board.comment.service;

import com.Super.Board.comment.dto.CommentDTO;
import com.Super.Board.post.repository.PostRepository;
import com.Super.Board.comment.entity.CommentEntity;
import com.Super.Board.comment.repository.CommentRepository;
import com.Super.Board.post.entity.PostEntity;
import com.Super.Board.user.entity.User;
import com.Super.Board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    public List<CommentDTO> findAll() {
        List<CommentEntity> commentEntityList = commentRepository.findAll();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            commentDTOList.add(CommentDTO.listDTO(commentEntity));
        }
        return commentDTOList;
    }

    public ResponseEntity<String> save(CommentDTO commentDTO) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(commentDTO.getPostId());
        Optional<User> optionalUser = userRepository.findById(commentDTO.getUserId());
        if (optionalPostEntity.isPresent()) {
            PostEntity postEntity = optionalPostEntity.get();
            User user = optionalUser.get();
            CommentEntity commentEntity = CommentEntity.saveEntity(commentDTO, postEntity, user);
            commentRepository.save(commentEntity);
            return ResponseEntity.status(200).body("댓글이 성공적으로 저장되었습니다.");
        } else {
            return ResponseEntity.status(200).body("댓글 저장이 실패하였습니다.");
        }
    }

    public Optional<CommentEntity> updateComment(String email, Long id, CommentDTO commentDTO) {
        Optional<CommentEntity> optionalComment = commentRepository.findById(id);

        if (optionalComment.isPresent()) {
            CommentEntity originComment = optionalComment.get();

            originComment.setContent(commentDTO.getContent());

            CommentEntity updatedComment = commentRepository.save(originComment);

            return Optional.of(updatedComment);
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteComment(String email, Long id) {
        Optional<CommentEntity> optionalComment = commentRepository.findById(id);

        if(optionalComment.isPresent()) {
            commentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
