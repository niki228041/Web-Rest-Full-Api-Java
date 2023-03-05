package org.example.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.entities.CategoryViewModel;
import org.example.entities.Entities_Realy.CategoryEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-05T14:36:06+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryViewModel categoryVMByEntity(CategoryEntity categoryViewModel) {
        if ( categoryViewModel == null ) {
            return null;
        }

        CategoryViewModel categoryViewModel1 = new CategoryViewModel();

        categoryViewModel1.setName( categoryViewModel.getName() );
        categoryViewModel1.setDescription( categoryViewModel.getDescription() );

        return categoryViewModel1;
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
}
