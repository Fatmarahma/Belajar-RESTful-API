package com.example.belajarRestApi.controllers;

import com.example.belajarRestApi.model.AuthenticationRequest;
import com.example.belajarRestApi.model.AuthenticationResponse;
import com.example.belajarRestApi.model.User;
import com.example.belajarRestApi.security.JWTUtil;
import com.example.belajarRestApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Endpoint untuk mendapatkan semua pengguna
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint untuk mendapatkan pengguna berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint untuk mendaftar pengguna baru
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Conflict jika username sudah ada
        }
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Endpoint untuk memperbarui pengguna
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint untuk menghapus pengguna
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint untuk register pengguna
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "409");
            response.put("message", "User already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Set role default jika belum ada
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName("USER"); // Set default role to "USER"
        }

        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // Endpoint untuk login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        final String jwtToken = jwtUtil.generateToken(authenticationRequest.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken)); // Pastikan AuthenticationResponse terimport dengan benar
    }
}

