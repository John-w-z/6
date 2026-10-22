package com.example.demo.service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Role;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> findAll() {
        return roleMapper.toResponseList(roleRepository.findAllByOrderByNameAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse findByName(String name) {
        Role role = roleRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + name));
        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional
    public RoleResponse create(String name) {

        if (name == null || name.isBlank()) {
            throw new BadRequestException("El nombre del rol es obligatorio");
        }

        String normalized = name.trim().toUpperCase();
        if (!normalized.startsWith("ROLE_")) {
            normalized = "ROLE_" + normalized;
        }

        if (roleRepository.existsByName(normalized)) {
            throw new BadRequestException("El rol ya existe: " + normalized);
        }

        Role saved = roleRepository.save(new Role(normalized));
        return roleMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id: " + id));
        roleRepository.delete(role);
    }
}