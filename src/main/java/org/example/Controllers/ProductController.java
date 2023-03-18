package org.example.Controllers;

import jakarta.validation.Valid;
import jakarta.xml.bind.DatatypeConverter;
import lombok.AllArgsConstructor;
import org.example.entities.*;
import org.example.entities.Dto.ProductCreateDTO;
import org.example.entities.Entities_Realy.ProductEntity;
import org.example.entities.Entities_Realy.ProductImageEntity;
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
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final StorageService storageService;
//    public static List<CategoryDTO> categoryDTOS = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<ProductViewModel>> index() throws IOException {
        var list = productRepository.findAll();

        var list_NEW = new ArrayList<ProductViewModel>();
        var values = "";
        for(var product:list){
            //var filename = product.getPhoto_name();
            //var resource = storageService.loadAsResource(filename);

            //var base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
            //        Paths.get(resource.getFile().toString())));

            var newProduct = new ProductViewModel();
            newProduct.setId(product.getId());
            newProduct.setName(product.getName());
            newProduct.setPrice(product.getPrice());
            newProduct.setDescriprion(product.getDescriprion());

            var categoryId = product.getCategory().getId();
            newProduct.setCategory_id(categoryId);

            ArrayList<String> imagesList = new ArrayList<>();

            for (var img:product.getProductImages())
            {

                var resource = storageService.loadAsResource("1200_" + img.getName());

                var base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
                        Paths.get(resource.getFile().toString())));

                imagesList.add(base64);
            }
            newProduct.setImagesInBytes(imagesList);

            list_NEW.add(newProduct);

        }
        return new ResponseEntity<>(list_NEW, HttpStatus.OK);
    }

//    @PostMapping("/getCategoryById")
//    public ResponseEntity<CategoryEntity> getCategoryById(@RequestBody FindCategoryByIdViewModel categoryId) throws IOException {
//        var category = productRepository.findById(categoryId.getId()).get();
//
//        var filename = category.getPhoto_name();
//        var resource = storageService.loadAsResource(filename);
//
//        var base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
//                Paths.get(resource.getFile().toString())));
//
//        var newCategory = new CategoryEntity(category.getId(),category.getName(),category.getDescription(),base64);
//
//        return new ResponseEntity<>(newCategory, HttpStatus.OK);
//    }

    @PostMapping(path = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createProduct(@ModelAttribute ProductCreateDTO product){

        ProductEntity new_product = new ProductEntity();
        new_product.setName(product.getName());
        new_product.setDescriprion(product.getDescriprion());
        new_product.setPrice(product.getPrice());
        var category = categoryRepository.findById(product.getCategory_id());
        new_product.setCategory(category.get());
        new_product.setCreated(new Date());
        new_product.setDeleted(false);

        var productEntity = productRepository.save(new_product);

        int iter = 0;

        for(var imageInBytes:product.getImages())
        {
            String filename = storageService.saveWithMultiePartFile(imageInBytes);
            ProductImageEntity imageEntity = new ProductImageEntity();
            imageEntity.setName(filename);
            imageEntity.setProduct(productEntity);
            imageEntity.setPriority(iter);
            imageEntity.setCreated(new Date());
            imageEntity.setDeleted(false);

            productImageRepository.save(imageEntity);
            iter++;
        }


        //new_product.setPhoto_name(filename);
        return "added " + product.toString();
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
