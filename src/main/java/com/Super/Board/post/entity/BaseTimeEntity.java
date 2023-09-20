package com.Super.Board.post.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseTimeEntity {

    @CreationTimestamp
    @Column(updatable = false ,name = "created_at")
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column (name = "updated_at")
    private LocalDateTime updatedAt;
}
