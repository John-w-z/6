package com.example.demo.controller;

import com.example.demo.dto.request.UserProfileRequest;
import com.example.demo.dto.response.UserProfileResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.IUserProfileService;
import com.example.demo.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    private final IUserProfileService userProfileService;
    private final IUserService userService;

    public UserProfileController(IUserProfileService userProfileService,
                                 IUserService userService) {
        this.userProfileService = userProfileService;
        this.userService = userService;
    }

    // ============================================================
    //  MI PERFIL
    // ============================================================
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> myProfile(Authentication auth) {
        UserResponse current = userService.findByUsername(auth.getName());
        return ResponseEntity.ok(userProfileService.findByUserId(current.getId()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> createOrUpdateMyProfile(
            Authentication auth,
            @Valid @RequestBody UserProfileRequest request) {

        UserResponse current = userService.findByUsername(auth.getName());
        return ResponseEntity.ok(userProfileService.createOrUpdate(current.getId(), request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyProfile(Authentication auth) {
        UserResponse current = userService.findByUsername(auth.getName());
        userProfileService.delete(current.getId());
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    //  ADMIN: perfil de cualquier usuario
    // ============================================================
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<UserProfileResponse> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.findByUserId(userId));
    }

    @PutMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileResponse> createOrUpdate(
            @PathVariable Long userId,
            @Valid @RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.createOrUpdate(userId, request));
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userProfileService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}