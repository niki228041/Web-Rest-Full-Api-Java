package org.example.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.entities.Dto.CategoryDTO;
import jakarta.xml.bind.DatatypeConverter;
import lombok.AllArgsConstructor;
import org.example.entities.Dto.CategoryEditDTO;
import org.example.entities.Dto.CreateCategoryWithMultipartFileDTO;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.example.entities.CategoryViewModel;
import org.example.entities.Dto.FindCategoryByIdDTO;
import org.example.interfaces.CategoryService;
import org.example.mapper.CategoryMapper;
import org.example.repository.CategoryRepository;
import org.example.storage.StorageService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
@SecurityRequirement(name = "vovan-api")
public class CategoryController {

    //Repositories
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;


    //mappers
    private final CategoryMapper categoryMapper;

    //services
    private final CategoryService categoryService;



    public static List<CategoryDTO> categoryDTOS = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> index() throws IOException  {
        return categoryService.index();
    }

    @PostMapping("/getCategoryById")
    public ResponseEntity<CategoryEntity> getCategoryById(@RequestBody FindCategoryByIdDTO categoryId) throws IOException {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> createCategory(@RequestBody CategoryViewModel category){
        return categoryService.create(category);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createCategory_MultipartFile(@ModelAttribute CreateCategoryWithMultipartFileDTO category){
        return categoryService.createWithMultipart(category);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(int id)
    {
        return categoryService.remove(id);
    }

    @PostMapping("/edit")
    public ResponseEntity<String> edit(@RequestBody CategoryEditDTO category_to_edit)
    {
        return categoryService.edit(category_to_edit);

    }


}
