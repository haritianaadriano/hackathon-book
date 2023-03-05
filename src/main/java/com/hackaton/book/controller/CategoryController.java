package com.hackaton.book.controller;

import com.hackaton.book.model.Category;
import com.hackaton.book.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@AllArgsConstructor
public class CategoryController {
    private CategoryService service;

    @GetMapping("/categories")
    public List<Category> getAllCategories(
            @RequestParam(name = "page", required = false)Long page,
            @RequestParam(name = "page_size", required = false)Long page_size,
            @RequestParam(name = "name", required = false)String name

    ){
        return service.redirectingRequest(page, page_size,name);
    }
    @GetMapping("/categories/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id){
        return service.getCategoryById(id);
    }

    @PostMapping("/categories")
    public Category insertCategory(@RequestBody Category category){
        return service.insertCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public String deleteId(@PathVariable(name = "id")Long id){
        return service.deleteById(id);
    }

    @PutMapping("/categories/{id}")
    public void updateCategories(
            @PathVariable Long id,
            @RequestBody Category category
    ){
        service.putUpdateCategory(id, category);
    }
    @PatchMapping("/categories/{id}")
    public void patchUser(
            @PathVariable Long id,
            @RequestBody Category newCategory
    ){
        service.patchUpdateCategory(id, newCategory);
    }
}