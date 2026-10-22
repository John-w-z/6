package com.example.demo.service;

import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.response.UserResponse;

import java.util.List;

public interface IUserService {

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    List<UserResponse> findAll();

    List<UserResponse> findByRole(String roleName);

    UserResponse update(Long id, UpdateUserRequest request);

    void changePassword(Long id, ChangePasswordRequest request);

    void enable(Long id);

    void disable(Long id);

    void delete(Long id);
}
