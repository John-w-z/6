package com.example.demo.service;

import com.example.demo.dao.PostRepository;
import com.example.demo.dao.TagRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.request.PostRequest;
import com.example.demo.dto.response.PostResponse;
import com.example.demo.entity.Post;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PostMapper;
import com.example.demo.mapper.TagMapper;
import com.example.demo.service.IPostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;
    private final TagMapper tagMapper;

    public PostService(PostRepository postRepository,
                           UserRepository userRepository,
                           TagRepository tagRepository,
                           PostMapper postMapper,
                           TagMapper tagMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.postMapper = postMapper;
        this.tagMapper = tagMapper;
    }

    // ============================================================
    //  CONSULTAS
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findByIdWithUserAndTags(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con id: " + id));
        return postMapper.toResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> findPublished(Pageable pageable) {
        return postRepository.findByPublishedTrue(pageable).map(postMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> findByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + userId);
        }
        return postMapper.toResponseList(postRepository.findByUserIdWithTags(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> findByTag(String tagName, Pageable pageable) {
        return postRepository.findByTagName(tagName, pageable).map(postMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> search(String term, Pageable pageable) {
        return postRepository.search(term, pageable).map(postMapper::toResponse);
    }

    // ============================================================
    //  ESCRITURA
    // ============================================================

    @Override
    @Transactional
    public PostResponse create(PostRequest request, String username) {

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        Post post = postMapper.toEntity(request);
        post.setUser(user);

        syncTags(post, request);

        Post saved = postRepository.save(post);
        return postMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PostResponse update(Long id, PostRequest request, String username) {

        Post post = postRepository.findByIdWithUserAndTags(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con id: " + id));

        verifyOwnership(post, username);

        postMapper.updateEntity(post, request);
        syncTags(post, request);

        Post updated = postRepository.save(post);
        return postMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public PostResponse publish(Long id, String username) {
        return changePublished(id, username, true);
    }

    @Override
    @Transactional
    public PostResponse unpublish(Long id, String username) {
        return changePublished(id, username, false);
    }

    // ============================================================
    //  ELIMINACIÓN
    // ============================================================

    @Override
    @Transactional
    public void delete(Long id, String username) {
        Post post = postRepository.findByIdWithUser(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con id: " + id));

        verifyOwnership(post, username);
        postRepository.delete(post);
    }

    // ============================================================
    //  HELPERS PRIVADOS
    // ============================================================

    private PostResponse changePublished(Long id, String username, boolean published) {
        Post post = postRepository.findByIdWithUser(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con id: " + id));

        verifyOwnership(post, username);

        post.setPublished(published);
        Post updated = postRepository.save(post);
        return postMapper.toResponse(updated);
    }

    private void verifyOwnership(Post post, String username) {
        if (post.getUser() == null || !post.getUser().getUsername().equals(username)) {
            throw new ForbiddenException("No tienes permiso para modificar este post");
        }
    }

    private void syncTags(Post post, PostRequest request) {
        if (request.getTags() == null) return;

        // Limpiar los tags actuales
        post.getTags().clear();

        for (String tagName : request.getTags()) {
            if (tagName == null || tagName.isBlank()) continue;

            String normalized = tagName.trim().toLowerCase();
            Tag tag = tagRepository.findByName(normalized)
                .orElseGet(() -> tagRepository.save(tagMapper.toEntity(normalized)));

            post.addTag(tag);
        }
    }
}