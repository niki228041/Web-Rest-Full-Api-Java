package org.example.entities.Entities_Realy;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String photo_name;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;

    public CategoryEntity(int id, String name, String description, String photo_name) {
        Id = id;
        this.name = name;
        this.description = description;
        this.photo_name = photo_name;
        this.products = new ArrayList<>();
    }

}
