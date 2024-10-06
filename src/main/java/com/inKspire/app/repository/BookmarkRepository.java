package com.inKspire.app.repository;

import com.inKspire.app.model.Blog;
import com.inKspire.app.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserId(Long userId);
    Optional<Bookmark> findByUserIdAndBlog(Long userId, Blog blog);
    List<Bookmark> findByBlog(Blog blog);
}
