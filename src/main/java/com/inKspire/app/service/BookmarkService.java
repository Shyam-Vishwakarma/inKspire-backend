package com.inKspire.app.service;

import com.inKspire.app.model.Blog;
import com.inKspire.app.model.Bookmark;
import com.inKspire.app.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BlogService blogService;
    public Bookmark createBookmark(Long userId, Long blogId) {
        Optional<Blog> blog = blogService.getBlogById(blogId);
        if(blog.isPresent()) {
            Bookmark bookmark = new Bookmark(userId, blog.get(), LocalDateTime.now());
            return bookmarkRepository.save(bookmark);
        } else throw new RuntimeException("blog do not exist");
    }

    public String deleteBookmark(Long userId, Long blogId) {
        Optional<Blog> blog = blogService.getBlogById(blogId);
        if (blog.isPresent()) {
            Bookmark bookmark = bookmarkRepository.findByUserIdAndBlog(userId, blog.get())
                    .orElseThrow(() -> new RuntimeException("Bookmark does not exist"));
            bookmarkRepository.delete(bookmark);
            return "Bookmark deleted successfully";
        } else {
            throw new RuntimeException("Blog does not exist");
        }
    }

    public String deleteBookmark(Long id) {
        Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bookmark do not exist"));
        bookmarkRepository.delete(bookmark);
        return "Bookmark deleted successfully";
    }

    public List<Blog> getAllBookmarkOfUser(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);
        List<Blog> blogs = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            blogs.add(bookmark.getBlog());
        }
        return blogs;
    }
}
