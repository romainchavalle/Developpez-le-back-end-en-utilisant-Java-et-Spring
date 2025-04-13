package com.rentals.house.service.impl;

import com.rentals.house.service.PictureService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@Service
public class PictureServiceImpl implements PictureService {

  private final String picturesPath = "pictures";

  public byte[] getPicture(String id) {
    File pictureFile = new File(picturesPath, id);

    System.out.println("Trying to read from path: " + pictureFile.getAbsolutePath());

    // When picture is not found, we throw an exception which will be caught by exception handler
    if (!pictureFile.exists()) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get picture");
    }

    // Reading the picture and returning it as byte array
    try (InputStream inputStream = new FileInputStream(pictureFile)) {
      return IOUtils.toByteArray(inputStream);
    } catch (IOException e) {
      System.out.println("Failed to get picture");
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get picture");
    }
  }

}
