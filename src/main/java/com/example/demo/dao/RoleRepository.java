package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // ========== Búsquedas básicas ==========

    Optional<Role> findByName(String name);

    Boolean existsByName(String name);

    List<Role> findByNameIn(List<String> names);

    // ========== Búsqueda con usuarios (JOIN FETCH) ==========

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.users WHERE r.name = :name")
    Optional<Role> findByNameWithUsers(@Param("name") String name);

    // ========== Buscar roles asignados a un usuario ==========

    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
    List<Role> findByUserId(@Param("userId") Long userId);

    // ========== Buscar roles asignados a un username ==========

    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.username = :username")
    List<Role> findByUsername(@Param("username") String username);

    // ========== Roles ordenados por nombre ==========

    List<Role> findAllByOrderByNameAsc();
}