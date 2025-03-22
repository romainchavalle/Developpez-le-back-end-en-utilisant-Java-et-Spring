package com.rentals.house.controller;

import com.rentals.house.model.Rental;
import com.rentals.house.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

  @Autowired
  private RentalService rentalService;

  // Endpoint pour récupérer tous les rentals
  @GetMapping
  public List<Rental> getAllRentals() {
    return rentalService.getAllRentals();
  }

  // Endpoint pour récupérer un rental par son id
  @GetMapping("/{id}")
  public Optional<Rental> getRentalById(@PathVariable Long id) {
    return rentalService.getRentalById(id);
  }

  // Endpoint pour créer un nouveau rental
  @PostMapping
  public Rental createRental(@RequestBody Rental rental) {
    return rentalService.saveRental(rental);
  }

  // Endpoint pour supprimer un rental
  @DeleteMapping("/{id}")
  public void deleteRental(@PathVariable Long id) {
    rentalService.deleteRental(id);
  }
}
