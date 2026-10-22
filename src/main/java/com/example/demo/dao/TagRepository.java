package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // ========== Búsquedas básicas ==========

    Optional<Tag> findByName(String name);

    Boolean existsByName(String name);

    List<Tag> findByNameIn(Set<String> names);

    // ========== Búsqueda con posts (JOIN FETCH) ==========

    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.posts WHERE t.name = :name")
    Optional<Tag> findByNameWithPosts(@Param("name") String name);

    @Query("SELECT DISTINCT t FROM Tag t LEFT JOIN FETCH t.posts WHERE t.id = :id")
    Optional<Tag> findByIdWithPosts(@Param("id") Long id);

    // ========== Tags usados en un post ==========

    @Query("SELECT t FROM Tag t JOIN t.posts p WHERE p.id = :postId")
    List<Tag> findByPostId(@Param("postId") Long postId);

    // ========== Tags ordenados por nombre ==========

    List<Tag> findAllByOrderByNameAsc();

    Page<Tag> findAllByOrderByNameAsc(Pageable pageable);

    // ========== Búsqueda por coincidencia ==========

    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Tag> search(@Param("term") String term);

    // ========== Tags más usados (top N) ==========

    @Query("SELECT t FROM Tag t LEFT JOIN t.posts p " +
           "GROUP BY t.id " +
           "ORDER BY COUNT(p) DESC")
    Page<Tag> findMostUsed(Pageable pageable);

    // ========== Contar posts por tag ==========

    @Query("SELECT COUNT(p) FROM Tag t JOIN t.posts p WHERE t.name = :name")
    long countPostsByTagName(@Param("name") String name);
}