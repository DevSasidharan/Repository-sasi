package com.example.Dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryDto {

    private  Long id;

    private String name;

    private String description;

    private MultipartFile multipartFile;
}
