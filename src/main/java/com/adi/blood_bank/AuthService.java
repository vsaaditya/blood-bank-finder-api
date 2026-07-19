package com.adi.blood_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    JwtUtil jwtUtil;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Register new user
    public String register(RegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists!";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepo.save(user);
        return "User registered successfully!";
    }

    // Login
    public String login(User user) {
        User existing = userRepo.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if (encoder.matches(user.getPassword(), existing.getPassword())) {
            return jwtUtil.generateToken(existing.getUsername(), existing.getRole());
        }
        return "Invalid credentials!";
    }
    public String promoteUser(Integer userId, String newRole) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        user.setRole(newRole);
        userRepo.save(user);
        return "User promoted to " + newRole + " successfully!";
    }
}