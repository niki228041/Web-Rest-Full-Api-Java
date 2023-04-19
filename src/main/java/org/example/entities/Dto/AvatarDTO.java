package org.example.entities.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AvatarDTO {
    private int user_id;

    private MultipartFile image;
}






