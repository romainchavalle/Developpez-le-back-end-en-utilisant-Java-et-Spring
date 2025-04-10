package com.rentals.house.controller;

import com.rentals.house.dto.RentalRequest;
import com.rentals.house.model.Rental;
import com.rentals.house.service.RentalService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

  // déclaration objet
  private final RentalService rentalService;

  // contructeur (peut-être remplacé par autowired)
  public RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  // définition des routes et traitement methodes
  @GetMapping
  public Map<String, List<Rental>> getAllRentals() {
    List<Rental> rentals = this.rentalService.getAllRentals();
    return Map.of("rentals", rentals);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
    Optional<Rental> rental = rentalService.getRentalById(id);
    if (rental.isPresent()) {
      return ResponseEntity.ok(rental.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> createRental(@RequestPart("rentalRequest") RentalRequest rentalRequest,
                                                          @RequestPart("image") MultipartFile image) {
    if (rentalService.saveRental(rentalRequest, image)==null) {
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
