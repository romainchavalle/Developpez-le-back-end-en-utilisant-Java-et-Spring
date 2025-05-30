package com.rentals.house.controller;

import com.rentals.house.service.PictureService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pictures")
@RequiredArgsConstructor
public class PictureController {

  private final PictureService pictureService;

  // GET THE PICTURE TO DISPLAY WITH THE ASSOCIATE RENTAL, ID == File's name
  @Hidden
  @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public @ResponseBody byte[] getPicture(@PathVariable String id) {
    return pictureService.getPicture(id);
  }

}
