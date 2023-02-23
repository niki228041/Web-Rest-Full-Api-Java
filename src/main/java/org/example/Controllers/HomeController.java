package org.example.Controllers;


import lombok.AllArgsConstructor;
import org.example.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

@RestController
@AllArgsConstructor
public class HomeController {
    private final StorageService storageService;


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverfile(@PathVariable String filename) throws Exception{
        Resource file = storageService.loadAsResource(filename);
        String urlFileName = URLEncoder.encode("Cool whatever");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION,"filename =\""+urlFileName+"\"")
                .body(file);
    }
}
