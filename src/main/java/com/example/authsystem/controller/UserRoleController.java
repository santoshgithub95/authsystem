package com.example.authsystem.controller;

import com.example.authsystem.dto.UserRoleResponse;
import com.example.authsystem.entity.Role;
import com.example.authsystem.entity.User;
import com.example.authsystem.service.RoleService;
import com.example.authsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-roles")
public class UserRoleController {

    private final UserService userService;
    private final RoleService roleService;

    public UserRoleController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/{userId}/{roleId}")
    public User assignRoleToUser(@PathVariable Long userId,
                                 @PathVariable Long roleId) {

        Role role = roleService.getRoleById(roleId);
        return userService.assignRoleToUser(userId, role);
    }

    @GetMapping
    public List<UserRoleResponse> getUserRoleMapping() {

        List<User> users = userService.getAllUsers();
        List<UserRoleResponse> responseList = new ArrayList<>();

        for (User user : users) {
            for (Role role : user.getRoles()) {
                responseList.add(
                        new UserRoleResponse(
                                user.getId(),
                                user.getUsername(),
                                role.getId(),
                                role.getRoleName()
                        )
                );
            }
        }

        return responseList;
    }
}