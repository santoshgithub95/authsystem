package com.example.authsystem.controller;

import com.example.authsystem.dto.RolePermissionResponse;
import com.example.authsystem.entity.Permission;
import com.example.authsystem.entity.Role;
import com.example.authsystem.service.PermissionService;
import com.example.authsystem.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role-permissions")
public class RolePermissionController {

    private final RoleService roleService;
    private final PermissionService permissionService;

    public RolePermissionController(RoleService roleService,
                                    PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @PostMapping("/{roleId}/{permissionId}")
    public Role assignPermissionToRole(@PathVariable Long roleId,
                                       @PathVariable Long permissionId) {

        Permission permission = permissionService.getPermissionById(permissionId);

        return roleService.assignPermissionToRole(roleId, permission);
    }

    @GetMapping
    public List<RolePermissionResponse> getRolePermissionMapping() {

        List<Role> roles = roleService.getAllRoles();
        List<RolePermissionResponse> responseList = new ArrayList<>();

        for (Role role : roles) {
            for (Permission permission : role.getPermissions()) {
                responseList.add(
                        new RolePermissionResponse(
                                role.getId(),
                                role.getRoleName(),
                                permission.getId(),
                                permission.getPermissionName()
                        )
                );
            }
        }

        return responseList;
    }
}