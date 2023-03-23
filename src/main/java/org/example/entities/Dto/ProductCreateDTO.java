package org.example.entities.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductCreateDTO {
    private String name;

    private String descriprion;

    private double price;

    private int category_id;

    private List<MultipartFile> images;

}
