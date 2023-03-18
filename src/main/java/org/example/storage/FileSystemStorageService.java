package org.example.storage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import java.util.UUID;

import io.swagger.v3.oas.models.media.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;


@Service
public class FileSystemStorageService implements StorageService{
    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties){
        rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init(){
        try {
            if(!Files.exists(rootLocation)){
                Files.createDirectories(rootLocation);
            }
        }
        catch (IOException ex)
        {
            throw new StorageExeption("Faile");
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new StorageExeption("Files Problem! Do something!!!");
            }
        }catch (MalformedURLException e)
        {
            throw new StorageExeption("File not found" + filename);
        }
    }

    @Override
    public String save(String base64) {
        try {
            if(base64.isEmpty()) {
                throw new StorageExeption("Пустий base64");
            }
            UUID uuid = UUID.randomUUID();
            String randomFileName = uuid.toString()+".jpg";
            String [] charArray = base64.split(",");
            Base64.Decoder decoder = Base64.getDecoder();
            byte [] bytes = new byte[0];
            bytes = decoder.decode(charArray[1]);
            String folder = rootLocation.toString()+"/"+randomFileName;
            new FileOutputStream(folder).write(bytes);
            return randomFileName;
        } catch (IOException e) {
            throw new StorageExeption("Проблема перетворення та збереження base64", e);
        }
    }

    @Override
    public String saveWithMultiePartFile(MultipartFile multipartFile) {
        try {
            String ext = "jpg";
            UUID uuid = UUID.randomUUID();
            String randomFileName = uuid.toString()+"."+ext;

            byte[] bytes = new byte[0];
            bytes = multipartFile.getBytes();
            int [] imageSize = {32,150,300,600,1200};

            try (var byteStream = new ByteArrayInputStream(bytes))
            {
                var image = ImageIO.read(byteStream);
                for (int size : imageSize) {

                    String directory = rootLocation.toString() + "/" + size + "_" + randomFileName;

                    BufferedImage newImg = ImageUtils.resizeImage(image,ext=="jpg"
                    ?ImageUtils.IMAGE_JPEG : ImageUtils.IMAGE_PNG,size,size);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    ImageIO.write(newImg,ext,byteArrayOutputStream);
                    byte [] newBytes = byteArrayOutputStream.toByteArray();
                    FileOutputStream out = new FileOutputStream(directory);
                    out.write(newBytes);
                    out.close();
                }
            }

            return randomFileName;
        } catch (IOException e) {
            throw new StorageExeption("Проблема перетворення та збереження base64", e);
        }
    }

    @Override
    public String delete(String filename) {
       Path imagesPath  = rootLocation.resolve(filename);

        try {
            Files.delete(imagesPath);
            System.out.println("File "
                    + imagesPath.toAbsolutePath().toString()
                    + " successfully removed");
        } catch (IOException e) {
            System.err.println("Unable to delete "
                    + imagesPath.toAbsolutePath().toString()
                    + " due to...");
            e.printStackTrace();
        }
        return "hi";
    }

}
