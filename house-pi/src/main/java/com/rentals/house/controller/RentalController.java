package com.rentals.house.controller;

import com.rentals.house.model.Rental;
import com.rentals.house.service.RentalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
public class RentalController {

  // déclaration objet
  private final RentalService rentalService;

  // contructeur (peut-être remplacé par autowired)
  public RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  // définition des routes et traitement methodes
  @GetMapping
  public List<Rental> getAllRentals() {
    return rentalService.getAllRentals();
  }

  @GetMapping("/{id}")
  public Optional<Rental> getRentalById(@PathVariable Long id) {
    return rentalService.getRentalById(id);
  }

  @PostMapping
  public Rental createRental(@RequestBody Rental rental) {
    return rentalService.saveRental(rental);
  }

  @PutMapping("/{id}")
  public Rental updateRental(@RequestBody Rental rental) {
    return rentalService.updateRental(rental);
  }
}
