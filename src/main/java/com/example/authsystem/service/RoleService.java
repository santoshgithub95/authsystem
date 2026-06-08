package com.example.authsystem.service;

import com.example.authsystem.entity.Role;
import com.example.authsystem.repository.RoleRepository;
import org.springframework.stereotype.Service;
import com.example.authsystem.entity.Permission;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public Role updateRole(Long id, Role role) {

        Role existingRole = getRoleById(id);

        existingRole.setRoleName(role.getRoleName());

        return roleRepository.save(existingRole);
    }

    public String deleteRole(Long id) {

        Role existingRole = getRoleById(id);

        roleRepository.delete(existingRole);

        return "Role deleted successfully";
    }
    public Role assignPermissionToRole(Long roleId, Permission permission) {

        Role role = getRoleById(roleId);

        role.getPermissions().add(permission);

        return roleRepository.save(role);
    }
}