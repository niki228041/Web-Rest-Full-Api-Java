package org.example.mapper;

import org.example.entities.CategoryViewModel;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryViewModel categoryVMByEntity(CategoryEntity categoryViewModel);

    @Mapping(source = "photo_name",target = "photo")
    List<CategoryViewModel> categoryVMByEntities(List<CategoryEntity> list);
}
