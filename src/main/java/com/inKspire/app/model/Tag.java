package com.inKspire.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"blogs"})
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tagId;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnoreProperties("tags")
    Set<Blog> blogs = new HashSet<>();
}
