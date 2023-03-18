package org.example.services;


import jakarta.xml.bind.DatatypeConverter;
import lombok.AllArgsConstructor;
import org.example.entities.CategoryViewModel;
import org.example.entities.Dto.CategoryEditDTO;
import org.example.entities.Dto.CreateCategoryWithMultipartFileDTO;
import org.example.entities.Dto.FindCategoryByIdDTO;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.example.interfaces.CategoryService;
import org.example.mapper.CategoryMapper;
import org.example.repository.CategoryRepository;
import org.example.storage.FileSystemStorageService;
import org.example.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    //Repositories
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;



    //mappers
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<String> create(CategoryViewModel category) {

        CategoryEntity new_category = new CategoryEntity();
        new_category.setName(category.getName());
        new_category.setDescription(category.getDescription());

        new_category = categoryMapper.categoryEntityByVM(category);

        String filename = storageService.save(category.getPhoto());

        new_category.setPhoto_name(filename);
        categoryRepository.save(new_category);

        return new ResponseEntity<>("added " + category.toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> remove(int id) {
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

    @Override
    public ResponseEntity<String> edit(CategoryEditDTO category_to_edit) {
        CategoryEntity CategoryEntity_ = categoryRepository.findById(category_to_edit.getId()).get();

        var filename = storageService.save(category_to_edit.getPhoto());
        CategoryEntity_.setPhoto_name(filename);

//        categoryRepository.deleteById(oldEntity.getId());

        CategoryEntity_.setName(category_to_edit.getName());
        CategoryEntity_.setDescription(category_to_edit.getDescription());


        categoryRepository.save(CategoryEntity_);

        storageService.delete(CategoryEntity_.getPhoto_name());

        return new ResponseEntity<>( "edited " + category_to_edit.toString(), HttpStatus.OK);

    }

    @Override
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

    @Override
    public ResponseEntity<CategoryEntity> getCategoryById(FindCategoryByIdDTO categoryId) throws IOException {
        var category = categoryRepository.findById(categoryId.getId()).get();

        var filename = category.getPhoto_name();
        var resource = storageService.loadAsResource(filename);

        var base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
                Paths.get(resource.getFile().toString())));

        var newCategory = new CategoryEntity(category.getId(),category.getName(),category.getDescription(),base64);

        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> createWithMultipart(CreateCategoryWithMultipartFileDTO model) {

        CategoryEntity new_entity = categoryMapper.createCategoryWithMultipartFileDTO(model);
        String str =  storageService.saveWithMultiePartFile(model.getPhotoFile());

        return new ResponseEntity<>(str, HttpStatus.OK);
    }
}
