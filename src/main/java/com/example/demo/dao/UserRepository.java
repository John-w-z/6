package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ========== Búsquedas básicas ==========

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    // ========== Búsqueda con roles (JOIN FETCH para evitar N+1) ==========

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username")
    Optional<User> findByUsernameWithRoles(@Param("username") String username);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();

    // ========== Búsqueda con perfil ==========

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profile WHERE u.id = :id")
    Optional<User> findByIdWithProfile(@Param("id") Long id);

    // ========== Búsqueda con posts ==========

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts WHERE u.id = :id")
    Optional<User> findByIdWithPosts(@Param("id") Long id);

    // ========== Filtros por rol ==========

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findByRoleName(@Param("roleName") String roleName);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    Page<User> findByRoleName(@Param("roleName") String roleName, Pageable pageable);

    // ========== Filtros por estado ==========

    List<User> findByEnabledTrue();

    List<User> findByEnabledFalse();

    // ========== Búsqueda por coincidencia (LIKE) ==========

    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :term, '%'))")
    Page<User> search(@Param("term") String term, Pageable pageable);

    // ========== Usuarios creados después de X fecha ==========

    List<User> findByCreatedAtAfter(LocalDateTime date);

    // ========== Actualización masiva: habilitar/deshabilitar ==========

    @Modifying
    @Query("UPDATE User u SET u.enabled = :enabled, u.updatedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    int updateEnabled(@Param("id") Long id, @Param("enabled") Boolean enabled);

    // ========== Contar usuarios por rol ==========

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.name = :roleName")
    long countByRoleName(@Param("roleName") String roleName);
}