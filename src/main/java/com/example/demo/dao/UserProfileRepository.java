package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserProfile;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // ========== Búsqueda por usuario ==========

    Optional<UserProfile> findByUserId(Long userId);

    Optional<UserProfile> findByUserUsername(String username);

    Boolean existsByUserId(Long userId);

    // ========== Búsqueda con el usuario cargado (JOIN FETCH) ==========

    @Query("SELECT p FROM UserProfile p JOIN FETCH p.user WHERE p.id = :id")
    Optional<UserProfile> findByIdWithUser(@Param("id") Long id);

    @Query("SELECT p FROM UserProfile p JOIN FETCH p.user WHERE p.user.id = :userId")
    Optional<UserProfile> findByUserIdWithUser(@Param("userId") Long userId);

    // ========== Eliminar perfil por usuario ==========

    void deleteByUserId(Long userId);
}