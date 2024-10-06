package com.inKspire.app.controller;

import com.inKspire.app.dto.BookmarkRequest;
import com.inKspire.app.model.Blog;
import com.inKspire.app.model.Bookmark;
import com.inKspire.app.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {
    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping
    public Bookmark createBookmark(@RequestBody BookmarkRequest request) {
        return bookmarkService.createBookmark(request.getUserId(), request.getBlogId());
    }

    // /api/v1/bookmark?userId=124
    @GetMapping
    public List<Blog> getBookmarksOfUser(@RequestParam Long userId) {
        return bookmarkService.getAllBookmarkOfUser(userId);
    }

    @DeleteMapping
    public String deleteBookmark(@RequestBody BookmarkRequest request) {
        return bookmarkService.deleteBookmark(request.getUserId(), request.getBlogId());
    }

    // /api/v1/bookmark?bookmarkId=24
    @DeleteMapping("/{bookmarkId}")
    public String deleteBookmark(@PathVariable Long bookmarkId) {
        return bookmarkService.deleteBookmark(bookmarkId);
    }
}
