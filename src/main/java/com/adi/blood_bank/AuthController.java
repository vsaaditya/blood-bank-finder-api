package com.adi.blood_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService service;

    @PostMapping("/register")
    public String userRegister(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public   String userLogin(@RequestBody User user){

        return service.login(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/promote/{userId}")
    public String promoteUser(@PathVariable Integer userId,
                              @RequestParam String newRole) {
        return service.promoteUser(userId, newRole);
    }

}
