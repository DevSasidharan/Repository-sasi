package com.example.Controllers;

import com.example.Dtos.CategoryDto;
import com.example.Services.Auth.Admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> postcategory(@ModelAttribute CategoryDto categoryDto) throws IOException {
       CategoryDto createdCategory =  adminService.postCategory(categoryDto);
       if (createdCategory == null){
           return ResponseEntity.notFound().build();
       } else {
           return ResponseEntity.ok(createdCategory);
       }
    }
}
