package org.example.repository;


import org.example.entities.Entities_Realy.CategoryEntity;
import org.example.entities.Entities_Realy.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

}
