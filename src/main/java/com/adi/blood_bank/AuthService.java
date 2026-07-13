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
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists!";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");  // Always force USER for public registration!
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