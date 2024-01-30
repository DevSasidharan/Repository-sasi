package com.example.Dtos;

import com.example.Enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private Long userId;

    private String jwt;

    private UserRole userRole;

}
