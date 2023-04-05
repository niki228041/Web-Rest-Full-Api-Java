package org.example.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.entities.CategoryViewModel;
import org.example.entities.Dto.CreateCategoryWithMultipartFileDTO;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-29T16:45:58+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryViewModel categoryVMByEntity(CategoryEntity categoryModel) {
        if ( categoryModel == null ) {
            return null;
        }

        CategoryViewModel categoryViewModel = new CategoryViewModel();

        categoryViewModel.setName( categoryModel.getName() );
        categoryViewModel.setDescription( categoryModel.getDescription() );

        return categoryViewModel;
    }

    @Override
    public List<CategoryViewModel> categoryVMByEntities(List<CategoryEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<CategoryViewModel> list1 = new ArrayList<CategoryViewModel>( list.size() );
        for ( CategoryEntity categoryEntity : list ) {
            list1.add( categoryVMByEntity( categoryEntity ) );
        }

        return list1;
    }

    @Override
    public CategoryEntity categoryEntityByVM(CategoryViewModel categoryViewModel) {
        if ( categoryViewModel == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName( categoryViewModel.getName() );
        categoryEntity.setDescription( categoryViewModel.getDescription() );

        return categoryEntity;
    }

    @Override
    public CategoryEntity createCategoryWithMultipartFileDTO(CreateCategoryWithMultipartFileDTO CreateCategoryWithMultipartFileDTO) {
        if ( CreateCategoryWithMultipartFileDTO == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName( CreateCategoryWithMultipartFileDTO.getName() );
        categoryEntity.setDescription( CreateCategoryWithMultipartFileDTO.getDescription() );

        return categoryEntity;
    }
}
