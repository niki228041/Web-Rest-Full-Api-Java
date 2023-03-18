package org.example.entities.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCategoryWithMultipartFileDTO {
    private String name;
    private String description;
    private MultipartFile photoFile;
}
