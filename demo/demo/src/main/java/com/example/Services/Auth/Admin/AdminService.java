package com.example.Services.Auth.Admin;

import com.example.Dtos.CategoryDto;

import java.io.IOException;

public interface AdminService {
    CategoryDto postCategory(CategoryDto categoryDto) throws IOException;
}
