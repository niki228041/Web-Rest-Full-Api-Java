package org.example.entities.Entities_Realy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 255,nullable = false)
    private String name;

    @Column(length = 4255,nullable = false)
    private String descriprion;


    private int price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CategoryEntity category;

    @OneToMany(mappedBy = "product")
    private List<ProductImageEntity> productImages;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date created;

    private boolean isDeleted = false;
}
