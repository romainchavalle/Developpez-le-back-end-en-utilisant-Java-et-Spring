package com.rentals.house.controller;

import com.rentals.house.dto.RentalRequest;
import com.rentals.house.service.FileStorageService;
import com.rentals.house.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Rentals CRUD", description = "This API give you the CRUD of the rentals.")
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

  private final RentalService rentalService;
  private final FileStorageService fileStorageService;

  public RentalController(RentalService rentalService, FileStorageService fileStorageService) {
    this.rentalService = rentalService;
    this.fileStorageService = fileStorageService;
  }

  // DISPLAY ALL RENTALS
  @Operation(summary = "Get all the rentals", description = "Return a list of all the rentals.")
  @GetMapping
  public Map<String, List<RentalRequest>> getAllRentals() {
    List<RentalRequest> rentalRequests = this.rentalService.getAllRentals();
    return Map.of("rentals", rentalRequests);
  }

  // DISPLAY 1 SPECIFIC RENTAL
  @Operation(summary = "Get one specific rental", description = "The rental must be found with id given in parameters.")
  @GetMapping("/{id}")
  public ResponseEntity<RentalRequest> getRentalById(@PathVariable Long id) {
    Optional<RentalRequest> rental = rentalService.getRentalById(id);
    if (rental.isPresent()) {
      return ResponseEntity.ok(rental.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // CREATE A NEW RENTAL
  @Operation(summary = "Create a new rental", description = "The rental will be created from the form's inputs.")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> createRental( @RequestParam("name") String name,
                                                           @RequestParam("surface") Double surface,
                                                           @RequestParam("price") Double price,
                                                           @RequestParam("description") String description,
                                                           @RequestParam("picture") MultipartFile picture) {

    // Store the file in the 'picture' directory and return the url to call to access to that file
    String imageUrl = fileStorageService.storeFile(picture);

    // Mapping DTO with the differents parameters from the form
    RentalRequest rental = new RentalRequest();
    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setDescription(description);
    rental.setPicture(imageUrl);

    if (rentalService.saveRental(rental)==null) {
      return ResponseEntity.badRequest().body(Map.of("message", "Rental not created"));
    }
    else {
      return ResponseEntity.ok(Map.of("message", "Rental created!"));
    }
  }

  // UPDATE EXISTING RENTAL
  @Operation(summary = "Update an existing rental", description = "To update the rental, you need to give it's id in parameters.Then rental will be updated from the form's inputs.")
  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> updateRental(@PathVariable Long id,
                                                          @RequestParam("name") String name,
                                                          @RequestParam("surface") Double surface,
                                                          @RequestParam("price") Double price,
                                                          @RequestParam("description") String description) {


    // Mapping DTO with the differents parameters from the form
    RentalRequest updatedRental = new RentalRequest();
    updatedRental.setId(id);
    updatedRental.setName(name);
    updatedRental.setSurface(surface);
    updatedRental.setPrice(price);
    updatedRental.setDescription(description);

    rentalService.updateRental(id, updatedRental);

    return ResponseEntity.ok(Map.of("message", "Rental updated!"));
  }
}
