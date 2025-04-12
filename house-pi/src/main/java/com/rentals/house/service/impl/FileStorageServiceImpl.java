package com.rentals.house.service.impl;

import com.rentals.house.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {
  public String storeFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new RuntimeException("Le fichier est vide.");
    }
    try {
      String fileName = file.getOriginalFilename();

      // ajouter randomn id avant nom de fichier pour obtenir un nom unique.

      String uploadDirectory = System.getProperty("user.dir") + "/uploads";
      Path uploadPath = Paths.get(uploadDirectory);

      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      Path filePath = uploadPath.resolve(fileName); // Combine le chemin du dossier (uploadPath) avec le nom du fichier (fileName).
      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); // copy le fichier dans le dossier correspondant, ecrase celui existant si jamais même infos

      return "/images/" + fileName;
    } catch (IOException ex) {
      throw new RuntimeException("Erreur lors du stockage du fichier: " + file.getOriginalFilename(), ex);
    }
  }
}
