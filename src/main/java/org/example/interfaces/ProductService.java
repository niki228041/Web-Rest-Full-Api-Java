package org.example.interfaces;

import org.example.entities.CategoryViewModel;
import org.example.entities.Dto.*;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.example.entities.ProductViewModel;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ResponseEntity<String> create(ProductCreateDTO model);

    ResponseEntity<String> delete(FindByIdDTO productId) throws IOException;

//    ResponseEntity<String> remove(int id);
//    ResponseEntity<String> edit (CategoryEditDTO model);
    ResponseEntity<List<ProductViewModel>> index() throws IOException;
    ResponseEntity<ProductItemDTO> getById(FindByIdDTO productId) throws IOException;
}
