package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.UserResponse;

public interface IAuthService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
