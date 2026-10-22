package com.example.demo.controller;

import com.example.demo.dto.request.PostRequest;
import com.example.demo.dto.response.PostResponse;
import com.example.demo.service.IPostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    // ============================================================
    //  LECTURA PÚBLICA
    // ============================================================
    @GetMapping("/published")
    public ResponseEntity<Page<PostResponse>> findPublished(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(postService.findPublished(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<Page<PostResponse>> findByTag(
            @PathVariable String tagName,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(postService.findByTag(tagName, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostResponse>> search(
            @RequestParam String term,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(postService.search(term, pageable));
    }

    // ============================================================
    //  LECTURA AUTENTICADA
    // ============================================================
    @GetMapping
    public ResponseEntity<Page<PostResponse>> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.findByUserId(userId));
    }

    // ============================================================
    //  ESCRITURA
    // ============================================================
    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostRequest request,
                                               Authentication auth) {
        PostResponse response = postService.create(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody PostRequest request,
                                               Authentication auth) {
        return ResponseEntity.ok(postService.update(id, request, auth.getName()));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<PostResponse> publish(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(postService.publish(id, auth.getName()));
    }

    @PatchMapping("/{id}/unpublish")
    public ResponseEntity<PostResponse> unpublish(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(postService.unpublish(id, auth.getName()));
    }

    // ============================================================
    //  ELIMINACIÓN
    // ============================================================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isOwner(#id, authentication.name)")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        postService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}