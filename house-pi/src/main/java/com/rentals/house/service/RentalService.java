package com.rentals.house.service;

import com.rentals.house.model.Rental;
import com.rentals.house.repository.RentalRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class RentalService implements IRentalService{

  @Autowired
  private RentalRepository rentalRepository;

  // Méthode pour obtenir tous les rentals
  public List<Rental> getAllRentals() {
    return rentalRepository.findAll();
  }

  // Méthode pour obtenir un rental par son id
  public Optional<Rental> getRentalById(Long id) {
    return rentalRepository.findById(id);
  }

  // Méthode pour enregistrer un rental
  public Rental saveRental(Rental rental) {
    return rentalRepository.save(rental);
  }

  // Méthode pour supprimer un rental
  public void deleteRental(Long id) {
    rentalRepository.deleteById(id);
  }
}
