package com.example.demo.mapper;

import com.example.demo.dto.request.UserProfileRequest;
import com.example.demo.dto.response.UserProfileResponse;
import com.example.demo.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileResponse toResponse(UserProfile profile) {
        if (profile == null) return null;

        UserProfileResponse response = new UserProfileResponse();
        response.setId(profile.getId());
        response.setPhone(profile.getPhone());
        response.setAddress(profile.getAddress());
        response.setBirthDate(profile.getBirthDate());
        response.setAvatarUrl(profile.getAvatarUrl());
        response.setBio(profile.getBio());

        if (profile.getUser() != null) {
            response.setUserId(profile.getUser().getId());
        }
        return response;
    }

    public UserProfile toEntity(UserProfileRequest request) {
        if (request == null) return null;

        UserProfile profile = new UserProfile();
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setBirthDate(request.getBirthDate());
        profile.setAvatarUrl(request.getAvatarUrl());
        profile.setBio(request.getBio());
        return profile;
    }

    public void updateEntity(UserProfile profile, UserProfileRequest request) {
        if (profile == null || request == null) return;

        if (request.getPhone() != null) {
            profile.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            profile.setAddress(request.getAddress());
        }
        if (request.getBirthDate() != null) {
            profile.setBirthDate(request.getBirthDate());
        }
        if (request.getAvatarUrl() != null) {
            profile.setAvatarUrl(request.getAvatarUrl());
        }
        if (request.getBio() != null) {
            profile.setBio(request.getBio());
        }
    }
}
