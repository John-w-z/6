package com.example.demo.controller;

import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // ============================================================
    //  PERFIL PROPIO
    // ============================================================
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication auth) {
        return ResponseEntity.ok(userService.findByUsername(auth.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(Authentication auth,
                                                 @Valid @RequestBody UpdateUserRequest request) {
        UserResponse current = userService.findByUsername(auth.getName());
        return ResponseEntity.ok(userService.update(current.getId(), request));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changeMyPassword(Authentication auth,
                                                 @Valid @RequestBody ChangePasswordRequest request) {
        UserResponse current = userService.findByUsername(auth.getName());
        userService.changePassword(current.getId(), request);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    //  ADMIN: CRUD de usuarios
    // ============================================================
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

   @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/role/{roleName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> findByRole(@PathVariable String roleName) {
        return ResponseEntity.ok(userService.findByRole(roleName));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @PatchMapping("/{id}/enable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> enable(@PathVariable Long id) {
        userService.enable(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> disable(@PathVariable Long id) {
        userService.disable(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
