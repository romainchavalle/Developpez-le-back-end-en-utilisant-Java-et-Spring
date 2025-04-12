package com.rentals.house.controller;

import com.rentals.house.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pictures")
@RequiredArgsConstructor
public class PictureController {

  private final PictureService pictureService;

//  @Operation(
//    summary = "Get picture",
//    description = "Retrieve picture by id"
//  )
  @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public @ResponseBody byte[] getPicture(@PathVariable String id) {
    return pictureService.getPicture(id);
  }

}
