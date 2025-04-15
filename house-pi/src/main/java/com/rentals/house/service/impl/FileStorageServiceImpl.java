package com.rentals.house.service.impl;

import com.rentals.house.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

  // STORE FILES IN THE PICTURES DIRECTORY
  public String storeFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new RuntimeException("File is empty.");
    }
    try {
      // Create a unique file name for each file input
      String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

      // find the directory "pictures" or create it
      String uploadDirectory = System.getProperty("user.dir") + "/pictures";
      Path uploadPath = Paths.get(uploadDirectory);

      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      // Combine uploadPath and fileName then store the file
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      // return the file name -> it will be used to create the url used to get the picture (url is stored in database)
      return "/" + fileName;
    } catch (IOException ex) {
      throw new RuntimeException("Storage error in : " + file.getOriginalFilename(), ex);
    }
  }
}
