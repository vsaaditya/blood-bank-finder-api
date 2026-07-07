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
    public String register(User user) {
        // Check if username already exists
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists!";
        }
        // Encrypt password before saving
        user.setPassword(encoder.encode(user.getPassword()));
        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }
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
}