package com.example.authsystem.controller;

import com.example.authsystem.entity.Role;
import com.example.authsystem.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id,
                           @RequestBody Role role) {

        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }
}