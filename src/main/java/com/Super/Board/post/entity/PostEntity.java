package com.Super.Board.post.entity;

import com.Super.Board.post.dto.PostDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class PostEntity extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private String author;

    @Column
    private String title;

    @Column
    private String contents;

    //dto->entity
    //글작성





}
