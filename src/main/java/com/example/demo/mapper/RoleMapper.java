package com.example.demo.mapper;

import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role role) {
        if (role == null) return null;

        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        return response;
    }

    public List<RoleResponse> toResponseList(List<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public Set<RoleResponse> toResponseSet(Set<Role> roles) {
        if (roles == null) return Set.of();
        return roles.stream()
            .map(this::toResponse)
            .collect(Collectors.toSet());
    }
}
