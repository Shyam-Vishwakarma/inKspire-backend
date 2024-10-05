package com.inKspire.app.controller;

import com.inKspire.app.model.Tag;
import com.inKspire.app.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping()
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }
    @PostMapping()
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @DeleteMapping("/{tagId}")
    public String deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return "Tag deleted successfully!";
    }

    @PutMapping("/{tagId}")
    public Tag updateTag(@PathVariable Long tagId, @RequestBody Tag updatedTag) {
        return tagService.updateTag(tagId, updatedTag);
    }
}
