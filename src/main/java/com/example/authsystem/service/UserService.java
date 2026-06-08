package com.example.authsystem.service;

import com.example.authsystem.entity.User;
import com.example.authsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.authsystem.entity.Role;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setTenantId(user.getTenantId());

        return userRepository.save(existingUser);
    }

    public String deleteUser(Long id) {
        User existingUser = getUserById(id);
        userRepository.delete(existingUser);
        return "User deleted successfully";
    }
    public User assignRoleToUser(Long userId, Role role) {
        User user = getUserById(userId);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}