package org.example.entities.Dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductCreateDTO {
    private String name;

    private String descriprion;

    private int price;

    private int category_id;

    private List<String> imagesInBytes;

}
