package com.example.authsystem.service;

import com.example.authsystem.entity.Permission;
import com.example.authsystem.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
    }

    public Permission updatePermission(Long id, Permission permission) {
        Permission existingPermission = getPermissionById(id);
        existingPermission.setPermissionName(permission.getPermissionName());
        return permissionRepository.save(existingPermission);
    }

    public String deletePermission(Long id) {
        Permission existingPermission = getPermissionById(id);
        permissionRepository.delete(existingPermission);
        return "Permission deleted successfully";
    }
}