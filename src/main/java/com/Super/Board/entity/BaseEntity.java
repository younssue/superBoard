package com.Super.Board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AutoCloseable.class)
@Getter
public class BaseEntity {
    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;
}
