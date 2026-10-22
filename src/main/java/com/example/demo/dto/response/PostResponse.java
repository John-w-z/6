package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private Boolean published;
    private LocalDateTime createdAt;
    private Long userId;
    private String username;
    private Set<String> tags = new HashSet<>();

    public PostResponse() {
    }

    public PostResponse(Long id, String title, String content, Boolean published,
                        LocalDateTime createdAt, Long userId, String username, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.published = published;
        this.createdAt = createdAt;
        this.userId = userId;
        this.username = username;
        this.tags = tags;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Boolean getPublished() { return published; }
    public void setPublished(Boolean published) { this.published = published; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = tags; }
}