package org.example.Controllers;

import Dto.CategoryDTO;
import jakarta.xml.bind.DatatypeConverter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.entities.CategoryEntity;
import org.example.entities.CategoryViewModel;
import org.example.repository.CategoryRepository;
import org.example.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final StorageService storageService;
    public static List<CategoryDTO> categoryDTOS = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> index() throws IOException {
        var list = categoryRepository.findAll();
        var list_NEW = new ArrayList<CategoryEntity>();

        for(var category:list){
            var filename = category.getPhoto_name();
            var resource = storageService.loadAsResource(filename);

            var base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
                    Paths.get(resource.getFile().toString())));

            var newCategory = new CategoryEntity(category.getId(),category.getName(),category.getDescription(),base64);

            list_NEW.add(newCategory);
        }

        return new ResponseEntity<>(list_NEW, HttpStatus.OK);
    }

    @PostMapping("/add")
    public String createCategory(@RequestBody CategoryViewModel category){

        CategoryEntity new_category = new CategoryEntity();
        new_category.setName(category.getName());
        new_category.setDescription(category.getDescription());
        String filename = storageService.save(category.getPhoto());

        new_category.setPhoto_name(filename);
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
