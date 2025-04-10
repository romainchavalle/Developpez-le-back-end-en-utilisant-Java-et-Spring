package com.rentals.house.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

  public String storeFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new RuntimeException("Le fichier est vide.");
    }
    try {
      String fileName = file.getOriginalFilename();
      String uploadDirectory = "uploads/";
      Path uploadPath = Paths.get(uploadDirectory);
      Path filePath = uploadPath.resolve(fileName); // Combine le chemin du dossier (uploadPath) avec le nom du fichier (fileName).
      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); // copy le fichier dans le dossier correspondant, ecrase celui existant si jamais même infos

      return "/uploads/" + fileName;
    } catch (IOException ex) {
      throw new RuntimeException("Erreur lors du stockage du fichier: " + file.getOriginalFilename(), ex);
    }
  }
}
