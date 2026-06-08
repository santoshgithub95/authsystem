package com.example.authsystem.controller;

import com.example.authsystem.entity.Permission;
import com.example.authsystem.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public Permission createPermission(@RequestBody Permission permission) {
        return permissionService.createPermission(permission);
    }

    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping("/{id}")
    public Permission getPermissionById(@PathVariable Long id) {
        return permissionService.getPermissionById(id);
    }

    @PutMapping("/{id}")
    public Permission updatePermission(@PathVariable Long id,
                                       @RequestBody Permission permission) {
        return permissionService.updatePermission(id, permission);
    }

    @DeleteMapping("/{id}")
    public String deletePermission(@PathVariable Long id) {
        return permissionService.deletePermission(id);
    }
}