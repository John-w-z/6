package com.example.demo.service;

import com.example.demo.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {

    List<RoleResponse> findAll();

    RoleResponse findByName(String name);

    RoleResponse create(String name);

    void delete(Long id);
}