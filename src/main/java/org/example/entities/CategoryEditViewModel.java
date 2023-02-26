package org.example.entities;

import lombok.Data;

@Data
public class CategoryEditViewModel {
    private int id;
    private String name;
    private String description;
    private String photo;
}
