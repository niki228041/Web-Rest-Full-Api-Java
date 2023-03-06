package org.example.interfaces;

import org.example.entities.CategoryViewModel;
import org.example.entities.Dto.CategoryEditDTO;
import org.example.entities.Dto.FindCategoryByIdDTO;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    ResponseEntity<String> create(CategoryViewModel model);
    ResponseEntity<String> remove(int id);
    ResponseEntity<String> edit (CategoryEditDTO model);
    ResponseEntity<List<CategoryEntity>> index() throws IOException;
    ResponseEntity<CategoryEntity> getCategoryById(FindCategoryByIdDTO categoryId) throws IOException;
}
