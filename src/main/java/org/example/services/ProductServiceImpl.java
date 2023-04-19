package org.example.services;

import jakarta.xml.bind.DatatypeConverter;
import lombok.AllArgsConstructor;
import org.example.entities.CategoryViewModel;
import org.example.entities.Dto.FindByIdDTO;
import org.example.entities.Dto.FindCategoryByIdDTO;
import org.example.entities.Dto.ProductCreateDTO;
import org.example.entities.Dto.ProductItemDTO;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.example.entities.Entities_Realy.ProductEntity;
import org.example.entities.Entities_Realy.ProductImageEntity;
import org.example.entities.ProductViewModel;
import org.example.interfaces.ProductService;
import org.example.mapper.ProductMapper;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductImageRepository;
import org.example.repository.ProductRepository;
import org.example.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    //Repositories
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final StorageService storageService;


    //Mappers
    private final ProductMapper productMapper;


    @Override
    public ResponseEntity<String> create(ProductCreateDTO product) {
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
        return new ResponseEntity<>("added " + product.toString(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> delete(FindByIdDTO productId) throws IOException {
        try {
            ProductEntity productEntity = productRepository.findById(productId.getId()).get();
            for (var img:productEntity.getProductImages()) {
                storageService.deleteWithMultiePartFile(img.getName());
                productImageRepository.delete(img);
            }
            productRepository.delete(productEntity);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>("Product was not deleted " + ex.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product with id " + productId.getId().toString() +"Succesfuly deleted ", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductItemDTO> getById(FindByIdDTO productId) throws IOException {

        try {
            var product = productRepository.findById(productId.getId()).get();

//            var product_vm = productMapper.productItemByEntity(product);
            var product_vm = new ProductItemDTO();

            product_vm.setCategory_id(product.getCategory().getId());
            product_vm.setName(product.getName());
            product_vm.setPrice(product.getPrice());
            product_vm.setDescriprion(product.getDescriprion());
            var images = new ArrayList<String>();

            for (var item : product.getProductImages()) {
                var resource = storageService.loadAsResource("1200_" + item.getName());

                var base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
                        Paths.get(resource.getFile().toString())));

                images.add(base64);
            }

            product_vm.setImages(images);

            return new ResponseEntity<>(product_vm, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(new ProductItemDTO(), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<List<ProductViewModel>> index() throws IOException {
        var list = productRepository.findAll();

        var list_NEW = new ArrayList<ProductViewModel>();
        for(var product:list){

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
}
