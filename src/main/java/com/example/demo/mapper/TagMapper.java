package com.example.demo.mapper;

import com.example.demo.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagMapper {

    public String toName(Tag tag) {
        return tag != null ? tag.getName() : null;
    }

    public Set<String> toNameSet(Set<Tag> tags) {
        if (tags == null) return Set.of();
        return tags.stream()
            .map(Tag::getName)
            .collect(Collectors.toSet());
    }

    public List<String> toNameList(List<Tag> tags) {
        if (tags == null) return List.of();
        return tags.stream()
            .map(Tag::getName)
            .collect(Collectors.toList());
    }


    public Tag toEntity(String name) {
        if (name == null || name.isBlank()) return null;
        return new Tag(name.trim().toLowerCase());
    }

    public Set<Tag> toEntitySet(Set<String> names) {
        if (names == null) return Set.of();
        return names.stream()
            .filter(n -> n != null && !n.isBlank())
            .map(n -> new Tag(n.trim().toLowerCase()))
            .collect(Collectors.toSet());
    }
}
