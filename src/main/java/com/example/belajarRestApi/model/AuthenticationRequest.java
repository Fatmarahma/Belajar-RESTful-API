package com.example.belajarRestApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;


    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

