package com.inKspire.app.service;

import com.inKspire.app.model.Blog;
import com.inKspire.app.model.Tag;
import com.inKspire.app.repository.BlogRepository;
import com.inKspire.app.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private TagRepository tagRepository;

    public Blog createBlog(Blog blog) {
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }
    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> getAllBlogsOfAuthor(Long authorId) {
        return blogRepository.findByAuthorId(authorId);
    }

    public void deleteBlogById(Long id) {
        blogRepository.deleteById(id);
    }

    public Blog updateBlog(Long id, Blog updatedBlog) {
        return blogRepository.findById(id)
                .map(blog -> {
                    blog.setTitle(updatedBlog.getTitle());
                    blog.setContent(updatedBlog.getContent());
                    blog.setIsPublished(updatedBlog.getIsPublished());
                    blog.setUpdatedAt(LocalDateTime.now());
                    if(updatedBlog.getTags() != null) {
                        blog.getTags().addAll(updatedBlog.getTags());
                    }
                    return blogRepository.save(blog);
                }).orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    public Blog addTagsToBlog(Long blogId, Set<Long> tagIds) {
        return blogRepository.findById(blogId).map(blog -> {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(tagIds));
            blog.getTags().addAll(tags);
            return blogRepository.save(blog);
        }).orElseThrow(() -> new RuntimeException("blog not found"));
    }

    public Blog removeTagsFromBlog(Long blogId, Set<Long> tagIds) {
        return blogRepository.findById(blogId).map(blog -> {
            Set<Tag> tagsToRemove = new HashSet<>(tagRepository.findAllById(tagIds));
            blog.getTags().removeAll(tagsToRemove);
            return blogRepository.save(blog);
        }).orElseThrow(() -> new RuntimeException("blog not found"));
    }
}
