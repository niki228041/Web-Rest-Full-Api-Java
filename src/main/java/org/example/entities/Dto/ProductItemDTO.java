package org.example.entities.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
public class ProductItemDTO {
    private String name;

    private String descriprion;

    private double price;

    private int category_id;

    private List<String> images;

}
