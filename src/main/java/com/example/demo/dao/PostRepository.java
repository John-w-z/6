package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // ========== Búsquedas básicas ==========

    List<Post> findByUserId(Long userId);

    Page<Post> findByUserId(Long userId, Pageable pageable);

    List<Post> findByPublishedTrue();

    Page<Post> findByPublishedTrue(Pageable pageable);

    List<Post> findByPublishedFalse();

    // ========== Búsqueda por título ==========

    List<Post> findByTitleContainingIgnoreCase(String title);

    // ========== Búsqueda con autor (JOIN FETCH) ==========

    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.id = :id")
    Optional<Post> findByIdWithUser(@Param("id") Long id);

    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.user.id = :userId")
    List<Post> findByUserIdWithUser(@Param("userId") Long userId);

    // ========== Búsqueda con tags (JOIN FETCH) ==========

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.tags WHERE p.id = :id")
    Optional<Post> findByIdWithTags(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.tags WHERE p.user.id = :userId")
    List<Post> findByUserIdWithTags(@Param("userId") Long userId);

    // ========== Búsqueda con autor Y tags (todo en una consulta) ==========

    @Query("SELECT DISTINCT p FROM Post p " +
           "JOIN FETCH p.user " +
           "LEFT JOIN FETCH p.tags " +
           "WHERE p.id = :id")
    Optional<Post> findByIdWithUserAndTags(@Param("id") Long id);

    // ========== Filtrar por tag ==========

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.name = :tagName")
    Page<Post> findByTagName(@Param("tagName") String tagName, Pageable pageable);

    // ========== Búsqueda por término en título o contenido ==========

    @Query("SELECT p FROM Post p WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :term, '%'))")
    Page<Post> search(@Param("term") String term, Pageable pageable);

    // ========== Búsqueda por rango de fechas ==========

    List<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // ========== Posts publicados recientes ==========

    @Query("SELECT p FROM Post p WHERE p.published = true ORDER BY p.createdAt DESC")
    Page<Post> findRecentPublished(Pageable pageable);

    // ========== Actualización masiva: publicar/despublicar ==========

    @Modifying
    @Query("UPDATE Post p SET p.published = :published WHERE p.id = :id")
    int updatePublished(@Param("id") Long id, @Param("published") Boolean published);

    // ========== Contar posts por usuario ==========

    long countByUserId(Long userId);

    long countByUserIdAndPublishedTrue(Long userId);

    // ========== Eliminar todos los posts de un usuario ==========

    @Modifying
    void deleteByUserId(Long userId);
}