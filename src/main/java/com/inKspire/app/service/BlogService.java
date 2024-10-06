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
        if (blog.getTags() != null) {
            Set<Tag> tagsToAdd = getTagsFromTagObjects(blog.getTags());
            blog.getTags().clear();
            blog.getTags().addAll(tagsToAdd);
        }
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
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("blog do not exist"));
        for(Tag tag : blog.getTags()) {
            tag.getBlogs().remove(blog);
            tagRepository.save(tag);
        }
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
                        Set<Tag> tagsToAdd = getTagsFromTagObjects(updatedBlog.getTags());
                        blog.getTags().addAll(tagsToAdd);
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

    public Set<Blog> getBlogsByTagId(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new RuntimeException("tag do not exist"));
        return tag.getBlogs();
    }

    public Set<Tag> getTagsFromTagObjects(Set<Tag> tags) {
        Set<Long> tagIds = tags.stream().map(Tag::getTagId).collect(Collectors.toSet());
        return new HashSet<>(tagRepository.findAllById(tagIds));
    }
}
