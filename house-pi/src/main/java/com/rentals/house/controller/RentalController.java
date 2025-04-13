package com.rentals.house.controller;

import com.rentals.house.dto.RentalRequest;
import com.rentals.house.model.Rental;
import com.rentals.house.service.FileStorageService;
import com.rentals.house.service.RentalService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

  // déclaration objet
  private final RentalService rentalService;
  private final FileStorageService fileStorageService;

  // contructeur (peut-être remplacé par autowired)
  public RentalController(RentalService rentalService, FileStorageService fileStorageService) {
    this.rentalService = rentalService;
    this.fileStorageService = fileStorageService;
  }

  // définition des routes et traitement methodes
  @GetMapping
  public Map<String, List<RentalRequest>> getAllRentals() {
    List<RentalRequest> rentalRequests = this.rentalService.getAllRentals();
    return Map.of("rentals", rentalRequests);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RentalRequest> getRentalById(@PathVariable Long id) {
    Optional<RentalRequest> rental = rentalService.getRentalById(id);
    if (rental.isPresent()) {
      return ResponseEntity.ok(rental.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> createRental( @RequestParam("name") String name,
                                                           @RequestParam("surface") Double surface,
                                                           @RequestParam("price") Double price,
                                                           @RequestParam("description") String description,
                                                           @RequestParam("picture") MultipartFile picture) {

    String imageUrl = fileStorageService.storeFile(picture);

    RentalRequest rental = new RentalRequest();
    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setDescription(description);
    rental.setPicture(imageUrl);

    if (rentalService.saveRental(rental)==null) {
      return ResponseEntity.badRequest().body(Map.of("message", "Rental already exists"));
    }
    else {
      return ResponseEntity.ok(Map.of("message", "Rental created!"));
    }
  }

//  @PutMapping("/{id}")
//  public Rental updateRental(@RequestBody Rental rental) {
//    return rentalService.updateRental(rental);
//  }
}
