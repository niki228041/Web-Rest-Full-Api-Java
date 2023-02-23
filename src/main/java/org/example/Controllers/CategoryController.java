package org.example.Controllers;

import Dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.entities.CategoryEntity;
import org.example.entities.CategoryViewModel;
import org.example.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    public static List<CategoryDTO> categoryDTOS = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> index(){
        var list = categoryRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/add")
    public String createCategory(@RequestBody CategoryViewModel category){

        CategoryEntity new_category = new CategoryEntity();
        new_category.setName(category.getName());
        new_category.setDescription(category.getDescription());
        categoryRepository.save(new_category);
        return "added " + category.toString();
    }

    @DeleteMapping("/remove")
    public String remove(int id)
    {
        for (var category :categoryRepository.findAll()) {
            if(category.getId() == id)
            {
                categoryRepository.deleteById(category.getId());
                return "removed " + Integer.toString(id);

            }
        }
        return "didn't found category with " + Integer.toString(id) + " id";
    }

    @PutMapping("/edit")
    public String edit(@RequestBody CategoryEntity category_to_edit)
    {

        for (var category :categoryRepository.findAll()) {
            if(category.getId() == category_to_edit.getId())
            {
                categoryRepository.deleteById(category.getId());
                categoryRepository.save(category_to_edit);
                return "added " + category_to_edit.toString();
            }
        }
        return "not found category with " + category_to_edit.getId() + " id";

    }


}
