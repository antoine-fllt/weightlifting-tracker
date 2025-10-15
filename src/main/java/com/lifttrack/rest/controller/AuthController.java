package com.lifttrack.rest.controller;


import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Implémenter la logique JWT ici
        return ResponseEntity.ok(new AuthResponse("jwt-token-here", "username"));
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Implémenter la logique d'inscription
        return ResponseEntity.ok(new AuthResponse("jwt-token-here", request.getUsername()));
    }
    
    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }
    
    @Data
    static class RegisterRequest {
        private String username;
        private String email;
        private String password;
    }
    
    @Data
    @AllArgsConstructor
    static class AuthResponse {
        private String token;
        private String username;
    }
}
