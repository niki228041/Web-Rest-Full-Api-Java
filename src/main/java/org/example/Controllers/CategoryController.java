package org.example.Controllers;

import org.example.entities.Dto.CategoryDTO;
import jakarta.xml.bind.DatatypeConverter;
import lombok.AllArgsConstructor;
import org.example.entities.Dto.CategoryEditDTO;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.example.entities.CategoryViewModel;
import org.example.entities.Dto.FindCategoryByIdDTO;
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
@RequestMapping("api/categories")
public class CategoryController {

    //Repositories
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;


    //mappers
    private final CategoryMapper categoryMapper;


    public static List<CategoryDTO> categoryDTOS = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> index() throws IOException {
        var list = categoryRepository.findAll();
        var list_NEW = new ArrayList<CategoryEntity>();
//        var model = categoryMapper.categoryVMByEntities(list);
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

    @PostMapping("/getCategoryById")
    public ResponseEntity<CategoryEntity> getCategoryById(@RequestBody FindCategoryByIdDTO categoryId) throws IOException {
        var category = categoryRepository.findById(categoryId.getId()).get();

        var filename = category.getPhoto_name();
        var resource = storageService.loadAsResource(filename);

        var base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
                Paths.get(resource.getFile().toString())));

        var newCategory = new CategoryEntity(category.getId(),category.getName(),category.getDescription(),base64);

        return new ResponseEntity<>(newCategory, HttpStatus.OK);
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
    public ResponseEntity<String> remove(int id)
    {
        for (var category :categoryRepository.findAll()) {
            if(category.getId() == id)
            {
                storageService.delete(category.getPhoto_name());
                categoryRepository.deleteById(category.getId());
                return new ResponseEntity<>("removed" + Integer.toString(id), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( "don`t removed" + Integer.toString(id), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/edit")
    public String edit(@RequestBody CategoryEditDTO category_to_edit)
    {
        CategoryEntity CategoryEntity_ = categoryRepository.findById(category_to_edit.getId()).get();

        var filename = storageService.save(category_to_edit.getPhoto());
        CategoryEntity_.setPhoto_name(filename);

//        categoryRepository.deleteById(oldEntity.getId());

        CategoryEntity_.setName(category_to_edit.getName());
        CategoryEntity_.setDescription(category_to_edit.getDescription());


        categoryRepository.save(CategoryEntity_);

        storageService.delete(CategoryEntity_.getPhoto_name());

        return "added " + category_to_edit.toString();

    }


}
