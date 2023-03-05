package org.example.entities.Dto;

import lombok.Data;

@Data
public class CategoryEditDTO {
    private int id;
    private String name;
    private String description;
    private String photo;
}
