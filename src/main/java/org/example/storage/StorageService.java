package org.example.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
    void init();
    Resource loadAsResource(String filename);
    String save(String base64);
    public String delete(String filename);
    public String saveWithMultiePartFile(MultipartFile multipartFile);
}