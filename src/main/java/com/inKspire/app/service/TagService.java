package com.inKspire.app.service;

import com.inKspire.app.model.Tag;
import com.inKspire.app.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    public Tag updateTag(Long tagId, Tag updatedTag) {
        return tagRepository.findById(tagId).map(tag -> {
            tag.setName(updatedTag.getName());
            tag.setDescription(updatedTag.getDescription());
            return tagRepository.save(tag);
        }).orElseThrow(() -> new RuntimeException("tag not found!"));
    }
}
