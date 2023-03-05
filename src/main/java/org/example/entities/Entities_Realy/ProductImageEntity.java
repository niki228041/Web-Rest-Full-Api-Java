package org.example.entities.Entities_Realy;


import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "tbl_productImage")
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 255,nullable = false)
    private String name;

    private int priority;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date created;

    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    private ProductEntity product;

    private boolean isDeleted;
}
