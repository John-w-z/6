package com.example.demo.service;

import com.example.demo.dto.request.UserProfileRequest;
import com.example.demo.dto.response.UserProfileResponse;

public interface IUserProfileService {

    UserProfileResponse findByUserId(Long userId);

    UserProfileResponse createOrUpdate(Long userId, UserProfileRequest request);

    void delete(Long userId);
}
