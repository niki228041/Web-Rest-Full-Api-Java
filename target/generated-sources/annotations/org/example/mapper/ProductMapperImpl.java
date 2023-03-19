package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.entities.Dto.ProductItemDTO;
import org.example.entities.Entities_Realy.ProductEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-19T16:02:21+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductItemDTO productItemByEntity(ProductEntity categoryModel) {
        if ( categoryModel == null ) {
            return null;
        }

        ProductItemDTO productItemDTO = new ProductItemDTO();

        productItemDTO.setName( categoryModel.getName() );
        productItemDTO.setDescriprion( categoryModel.getDescriprion() );
        productItemDTO.setPrice( categoryModel.getPrice() );

        return productItemDTO;
    }
}
