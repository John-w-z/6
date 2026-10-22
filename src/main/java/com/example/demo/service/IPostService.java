package com.example.demo.service;

import com.example.demo.dto.request.PostRequest;
import com.example.demo.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostService {


    PostResponse findById(Long id);

    Page<PostResponse> findAll(Pageable pageable);

    Page<PostResponse> findPublished(Pageable pageable);

    List<PostResponse> findByUserId(Long userId);

    Page<PostResponse> findByTag(String tagName, Pageable pageable);

    Page<PostResponse> search(String term, Pageable pageable);


    PostResponse create(PostRequest request, String username);

    PostResponse update(Long id, PostRequest request, String username);

    PostResponse publish(Long id, String username);

    PostResponse unpublish(Long id, String username);


    void delete(Long id, String username);
}
