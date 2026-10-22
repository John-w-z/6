package com.example.demo.service;

import com.example.demo.dao.TagRepository;
import com.example.demo.entity.Tag;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.TagMapper;
import com.example.demo.service.ITagService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService implements ITagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAll() {
        return tagMapper.toNameList(tagRepository.findAllByOrderByNameAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findMostUsed(int limit) {
        return tagRepository
            .findMostUsed(PageRequest.of(0, limit))
            .map(Tag::getName)
            .getContent();
    }

    /**
     * Dado un conjunto de nombres, devuelve los nombres normalizados que existen
     * (creándolos si no existen). Útil antes de asociar tags a un Post.
     */
    @Override
    @Transactional
    public Set<String> resolveTags(Set<String> names) {
        if (names == null || names.isEmpty()) return Set.of();

        return names.stream()
            .filter(n -> n != null && !n.isBlank())
            .map(n -> n.trim().toLowerCase())
            .map(normalized -> tagRepository.findByName(normalized)
                .orElseGet(() -> tagRepository.save(tagMapper.toEntity(normalized))))
            .map(Tag::getName)
            .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag no encontrado con id: " + id));
        tagRepository.delete(tag);
    }
}