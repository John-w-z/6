package com.example.demo.controller;

import com.example.demo.service.ITagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final ITagService tagService;

    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    // ============================================================
    //  LECTURA PÚBLICA
    // ============================================================
    @GetMapping
    public ResponseEntity<List<String>> findAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/most-used")
    public ResponseEntity<List<String>> findMostUsed(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(tagService.findMostUsed(limit));
    }

    // ============================================================
    //  ADMIN
    // ============================================================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}