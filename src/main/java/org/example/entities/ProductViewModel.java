package org.example.entities;


import lombok.Data;

import java.util.List;

@Data

public class ProductViewModel {
    private int id;
    private String name;

    private String descriprion;

    private double price;

    private int category_id;

    private List<String> imagesInBytes;
}
