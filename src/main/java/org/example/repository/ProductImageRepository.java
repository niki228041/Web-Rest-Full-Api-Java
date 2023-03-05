package org.example.repository;


import org.example.entities.Entities_Realy.ProductEntity;
import org.example.entities.Entities_Realy.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity,Integer> {

}
