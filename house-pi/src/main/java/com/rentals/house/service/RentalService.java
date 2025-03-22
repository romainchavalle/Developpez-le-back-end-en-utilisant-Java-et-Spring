package com.rentals.house.service;

import com.rentals.house.model.Rental;
import com.rentals.house.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

  // déclaration objet
  private final RentalRepository rentalRepository;

  // contructeur (peut-être remplacé par autowired)
  public RentalService(RentalRepository rentalRepository) {
    this.rentalRepository = rentalRepository;
  }

  // Méthodes (ces methods existent grâce à JPA dans le repo)
  public List<Rental> getAllRentals() {
    return rentalRepository.findAll();
  }

  public Optional<Rental> getRentalById(Long id) {
    return rentalRepository.findById(id);
  }

  public Rental saveRental(Rental rental) {
    return rentalRepository.save(rental);
  }

  public Rental updateRental(Rental rental){
    return rentalRepository.save(rental);
  }

}
