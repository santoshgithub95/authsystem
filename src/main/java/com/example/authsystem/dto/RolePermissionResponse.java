package com.example.authsystem.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionResponse {

    private Long roleId;
    private String roleName;
    private Long permissionId;
    private String permissionName;
}