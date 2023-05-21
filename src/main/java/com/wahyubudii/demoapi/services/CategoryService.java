package com.wahyubudii.demoapi.services;

import com.wahyubudii.demoapi.models.entities.Category;
import com.wahyubudii.demoapi.models.repositories.CategoryRepository;
import jakarta.transaction.TransactionScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@TransactionScoped
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public Category save(@RequestBody Category category) {
        log.info("Category saved!");
        return categoryRepository.save(category);
    }

    public Iterable<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getSingleCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent()) {
            return null;
        }
        return category.get();
    }

    public String deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        log.info("Category {} is deleted", id);
        return "Delete Successfully";
    }
}
