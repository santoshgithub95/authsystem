package com.example.authsystem.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponse {

    private Long userId;
    private String username;
    private Long roleId;
    private String roleName;
}