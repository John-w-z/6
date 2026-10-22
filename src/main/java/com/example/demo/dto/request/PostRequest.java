package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class PostRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
    private String title;

    @NotBlank(message = "El contenido es obligatorio")
    @Size(max = 5000, message = "El contenido no puede superar los 5000 caracteres")
    private String content;

    private Boolean published;

    @Size(max = 10, message = "Máximo 10 tags por post")
    private Set<@NotBlank(message = "El tag no puede estar vacío") String> tags = new HashSet<>();

    public PostRequest() {
    }

    public PostRequest(String title, String content, Boolean published, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.published = published;
        if (tags != null) this.tags = tags;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Boolean getPublished() { return published; }
    public void setPublished(Boolean published) { this.published = published; }

    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = tags; }
}