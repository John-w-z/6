package com.example.demo.mapper;

import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthResponse toResponse(String token, String tokenType,
                                   Long expiresIn, UserResponse user) {
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setTokenType(tokenType);
        response.setExpiresIn(expiresIn);
        response.setUser(user);
        return response;
    }
}
