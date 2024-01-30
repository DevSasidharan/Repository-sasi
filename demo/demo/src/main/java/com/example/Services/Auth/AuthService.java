package com.example.Services.Auth;

import com.example.Dtos.SignupRequest;
import com.example.Dtos.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
}
