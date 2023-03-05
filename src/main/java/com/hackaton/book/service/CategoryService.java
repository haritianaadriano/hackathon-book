package com.hackaton.book.service;

import com.hackaton.book.model.Category;
import com.hackaton.book.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository repository;

    //GET MAPPING
    public List<Category> redirectingRequest(Long page, Long page_size, String name){
        if(name != null){
            if (page== null && page_size==null) {
                return this.getCategoryByName(name);
            }
        }else if (page!= null && page_size==null) {
            return this.getCategoriesBySpecificPage(page, 1L);
        }else if (page != null) {
            return this.getCategoriesBySpecificPage(page, page_size);
        }
        return this.allCategory();
    }
    public List<Category> getCategoriesBySpecificPage(Long page, Long pageSize){
        if(page != null && pageSize != null){
            Pageable pageable = PageRequest.of(Math.toIntExact(page-1), Math.toIntExact(pageSize));
            return repository.findAll(pageable).toList();
        }else {
            return repository.findAll();
        }
    }
    public List<Category> allCategory(){
        return repository.findAll();
    }
    public List<Category> getCategoryByName(String categoryName){
        return repository.findByNameCategoryContainingIgnoreCase(categoryName);
    }
    public Optional<Category> getCategoryById(Long id){
        return repository.findById(id);
    }

    //POST MAPPING
    public Category insertCategory(Category category){
        return repository.save(category);
    }

    //DELETE MAPPING
    public String deleteById(Long id){
        repository.deleteById(id);
        return "Item deleted successfully";
    }

    //PUT MAPPING
    public void putUpdateCategory(Long id, Category newCategory){
        List<Category> categories = allCategory();
        for(Category categoryInfo : categories){
            if(categoryInfo.getIdCategory() == id){
                if (!(newCategory.getNameCategory() == null)){
                    categoryInfo.setNameCategory(newCategory.getNameCategory());
                }
                repository.save(categoryInfo);
            }
        }
    }

    //PATCH MAPPING
    public void patchUpdateCategory(Long id, Category newCategory){
        List<Category> categories = allCategory();
        for(Category categoryInfo : categories){
            if(categoryInfo.getIdCategory() == id){
                categoryInfo.setNameCategory(newCategory.getNameCategory());
                repository.save(categoryInfo);
            }
        }
    }
}