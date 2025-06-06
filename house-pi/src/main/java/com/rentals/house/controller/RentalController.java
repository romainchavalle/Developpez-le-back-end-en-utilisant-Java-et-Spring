package com.rentals.house.controller;

import com.rentals.house.dto.MessageResponse;
import com.rentals.house.dto.RentalRequest;
import com.rentals.house.dto.RentalResponse;
import com.rentals.house.dto.RentalResponseWrapper;
import com.rentals.house.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Rentals CRUD", description = "This API give you the CRUD of the rentals.")
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

  private final RentalService rentalService;

  public RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  // DISPLAY ALL RENTALS
  @Operation(summary = "Get all the rentals", description = "Return a list of all the rentals.")
  @GetMapping
  public RentalResponseWrapper getAllRentals() {
    List<RentalResponse> rentalResponses = this.rentalService.getAllRentals();
    return new RentalResponseWrapper(rentalResponses);
  }

  // DISPLAY 1 SPECIFIC RENTAL
  @Operation(summary = "Get one specific rental", description = "The rental must be found with id given in parameters.")
  @GetMapping("/{id}")
  public RentalResponse getRentalById(@PathVariable Long id) {
    return rentalService.getRentalById(id);
  }

  // CREATE A NEW RENTAL
  @Operation(summary = "Create a new rental", description = "The rental will be created from the form's inputs.")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<MessageResponse> createRental(@ModelAttribute RentalRequest rental) {
    rentalService.saveRental(rental);
    MessageResponse messageResponse = new MessageResponse("Rental created!");
    return ResponseEntity.ok(messageResponse);
  }

  // UPDATE EXISTING RENTAL
  @Operation(summary = "Update an existing rental", description = "To update the rental, you need to give it's id in parameters.Then rental will be updated from the form's inputs.")
  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<MessageResponse> updateRental(@PathVariable Long id,
                                                          @ModelAttribute RentalRequest rental) {

    rentalService.updateRental(id, rental);
    MessageResponse messageResponse = new MessageResponse("Rental updated!");
    return ResponseEntity.ok(messageResponse);
  }
}
