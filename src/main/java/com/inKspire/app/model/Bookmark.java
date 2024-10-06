package com.inKspire.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "bookmarks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "blog_id"})
})
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "blog_id")

    private Blog blog;

    private LocalDateTime createdAt;

    public Bookmark(Long userId, Blog blog, LocalDateTime createdAt) {
        this.userId = userId;
        this.blog = blog;
        this.createdAt = createdAt;
    }
}
