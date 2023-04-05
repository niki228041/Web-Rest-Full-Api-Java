package org.example.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.xml.bind.DatatypeConverter;
import lombok.AllArgsConstructor;
import org.example.entities.*;
import org.example.entities.Dto.FindByIdDTO;
import org.example.entities.Dto.ProductCreateDTO;
import org.example.entities.Dto.ProductItemDTO;
import org.example.entities.Entities_Realy.ProductEntity;
import org.example.entities.Entities_Realy.ProductImageEntity;
import org.example.interfaces.CategoryService;
import org.example.interfaces.ProductService;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductImageRepository;
import org.example.repository.ProductRepository;
import org.example.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/products")
@SecurityRequirement(name = "vovan-api")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final StorageService storageService;

    private final ProductService productService;

//    public static List<CategoryDTO> categoryDTOS = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<ProductViewModel>> index() throws IOException {
        return productService.index();
    }

    @PostMapping("/getProductById")
    public ResponseEntity<ProductItemDTO> getProductById(@RequestBody FindByIdDTO id) throws IOException {

        return productService.getById(id);
    }

    @PostMapping("/deleteProductById")
    public ResponseEntity<String> deleteProductById(@RequestBody FindByIdDTO id) throws IOException {

        return productService.delete(id);
    }

    @PostMapping(path = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProduct(@ModelAttribute ProductCreateDTO product){
        return productService.create(product);
    }

//    @DeleteMapping("/remove")
//    public ResponseEntity<String> remove(int id)
//    {
//        for (var category :productRepository.findAll()) {
//            if(category.getId() == id)
//            {
//                storageService.delete(category.getPhoto_name());
//                productRepository.deleteById(category.getId());
//                return new ResponseEntity<>("removed" + Integer.toString(id), HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity<>( "don`t removed" + Integer.toString(id), HttpStatus.BAD_REQUEST);
//    }
//
//    @PostMapping("/edit")
//    public String edit(@RequestBody CategoryEditViewModel category_to_edit)
//    {
//        CategoryEntity CategoryEntity_ = productRepository.findById(category_to_edit.getId()).get();
//
//        var filename = storageService.save(category_to_edit.getPhoto());
//        CategoryEntity_.setPhoto_name(filename);
//
////        categoryRepository.deleteById(oldEntity.getId());
//
//        CategoryEntity_.setName(category_to_edit.getName());
//        CategoryEntity_.setDescription(category_to_edit.getDescription());
//
//
//        productRepository.save(CategoryEntity_);
//
//        storageService.delete(CategoryEntity_.getPhoto_name());
//
//        return "added " + category_to_edit.toString();
//
//    }




}
