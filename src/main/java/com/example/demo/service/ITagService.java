package com.example.demo.service;

import java.util.List;
import java.util.Set;

public interface ITagService {

    List<String> findAll();

    List<String> findMostUsed(int limit);

    Set<String> resolveTags(Set<String> names);

    void delete(Long id);
}
