package org.example.mapper;

import org.example.entities.CategoryViewModel;
import org.example.entities.Dto.CreateCategoryWithMultipartFileDTO;
import org.example.entities.Dto.ProductItemDTO;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.example.entities.Entities_Realy.ProductEntity;
import org.example.entities.ProductViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductItemDTO productItemByEntity(ProductEntity categoryModel);
}
