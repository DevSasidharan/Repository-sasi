package com.example.Services.Auth.Admin;

import com.example.Dtos.CategoryDto;
import com.example.Entities.Category;
import com.example.Repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    @Autowired
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryDto postCategory(CategoryDto categoryDto) throws IOException {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImg(categoryDto.getMultipartFile().getBytes());
        Category createdcat =  categoryRepository.save(category);
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setId(createdcat.getId());
        return categoryDto1;
    }
}
