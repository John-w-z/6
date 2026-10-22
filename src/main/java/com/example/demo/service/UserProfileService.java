package com.example.demo.service;

import com.example.demo.dao.UserProfileRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.request.UserProfileRequest;
import com.example.demo.dto.response.UserProfileResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserProfileMapper;
import com.example.demo.service.IUserProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService implements IUserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileService(UserProfileRepository userProfileRepository,
                                  UserRepository userRepository,
                                  UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse findByUserId(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Perfil no encontrado para el usuario con id: " + userId));
        return userProfileMapper.toResponse(profile);
    }

    @Override
    @Transactional
    public UserProfileResponse createOrUpdate(Long userId, UserProfileRequest request) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + userId));

        UserProfile profile = userProfileRepository.findByUserId(userId).orElse(null);

        if (profile == null) {
            // Crear
            profile = userProfileMapper.toEntity(request);
            user.setProfile(profile);       // sincroniza ambos lados (helper)
        } else {
            // Actualizar
            userProfileMapper.updateEntity(profile, request);
        }

        UserProfile saved = userProfileRepository.save(profile);
        return userProfileMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Perfil no encontrado para el usuario con id: " + userId));
        userProfileRepository.delete(profile);
    }
}