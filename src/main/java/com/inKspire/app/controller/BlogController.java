package com.inKspire.app.controller;

import com.inKspire.app.model.Blog;
import com.inKspire.app.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping()
    public Blog createBlog(@RequestBody Blog blog) {
        return blogService.createBlog(blog);
    }
    @GetMapping()
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{blogId}")
    public Optional<Blog> getBlogById(@PathVariable Long blogId) {
        return blogService.getBlogById(blogId);
    }

    // /api/v1/blogs/author?authorId=123
    @GetMapping("/author")
    public List<Blog> getAllBlogsOfAuthor(@RequestParam Long authorId) {
        return blogService.getAllBlogsOfAuthor(authorId);
    }

    @GetMapping("/tag")
    public Set<Blog> getAllBlogsByTagId(@RequestParam Long tagId) {
        return blogService.getBlogsByTagId(tagId);
    }

    @PutMapping("/{blogId}")
    public Blog updateBlog(@PathVariable Long blogId, @RequestBody Blog updatedBlog) {
        return blogService.updateBlog(blogId, updatedBlog);
    }

    @DeleteMapping("/{blogId}")
    public String deleteBlog(@PathVariable Long blogId) {
        blogService.deleteBlogById(blogId);
        return "Blog deleted successfully";
    }

    @PutMapping("/{blogId}/tags")
    public Blog addTagsToBlog(@PathVariable Long blogId, @RequestBody Set<Long> tagIds) {
        return blogService.addTagsToBlog(blogId, tagIds);
    }

    @DeleteMapping("/{blogId}/tags")
    public Blog removeTagsFromBlog(@PathVariable Long blogId, @RequestBody Set<Long> tagIds) {
        return blogService.removeTagsFromBlog(blogId, tagIds);
    }
}