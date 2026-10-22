package com.example.demo.mapper;

import com.example.demo.dto.request.PostRequest;
import com.example.demo.dto.response.PostResponse;
import com.example.demo.entity.Post;
import com.example.demo.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    public PostResponse toResponse(Post post) {
        if (post == null) return null;

        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setPublished(post.getPublished());
        response.setCreatedAt(post.getCreatedAt());

        if (post.getUser() != null) {
            response.setUserId(post.getUser().getId());
            response.setUsername(post.getUser().getUsername());
        }

        if (post.getTags() != null) {
            Set<String> tagNames = post.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
            response.setTags(tagNames);
        } else {
            response.setTags(new HashSet<>());
        }

        return response;
    }

    public List<PostResponse> toResponseList(List<Post> posts) {
        if (posts == null) return List.of();
        return posts.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public Post toEntity(PostRequest request) {
        if (request == null) return null;

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPublished(request.getPublished() != null ? request.getPublished() : false);
        return post;
    }
    public void updateEntity(Post post, PostRequest request) {
        if (post == null || request == null) return;

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            post.setTitle(request.getTitle());
        }
        if (request.getContent() != null && !request.getContent().isBlank()) {
            post.setContent(request.getContent());
        }
        if (request.getPublished() != null) {
            post.setPublished(request.getPublished());
        }
    }
}