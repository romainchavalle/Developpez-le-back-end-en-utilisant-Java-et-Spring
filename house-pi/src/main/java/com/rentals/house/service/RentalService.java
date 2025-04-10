package com.rentals.house.service;

import com.rentals.house.dto.RentalRequest;
import com.rentals.house.model.Rental;
import com.rentals.house.model.User;
import com.rentals.house.repository.RentalRepository;
import com.rentals.house.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

  // déclaration objet
  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;
  private final FileStorageService fileStorageService;

  // contructeur (peut-être remplacé par autowired)
  public RentalService(RentalRepository rentalRepository, UserRepository userRepository,FileStorageService fileStorageService) {
    this.userRepository = userRepository;
    this.rentalRepository = rentalRepository;
    this.fileStorageService = fileStorageService;
  }

  // Méthodes (ces methods existent grâce à JPA dans le repo)
  public List<Rental> getAllRentals() {
    return rentalRepository.findAll();
  }

  public Optional<Rental> getRentalById(Long id) {
    return rentalRepository.findById(id);
  }

  public Rental saveRental(RentalRequest rentalRequest, MultipartFile image) {
    User owner = this.userRepository.findById(rentalRequest.getOwnerId())
      .orElseThrow(() -> new RuntimeException("User not found"));

    String imageUrl = fileStorageService.storeFile(image);

    Rental rental = new Rental();
    rental.setName(rentalRequest.getName());
    rental.setSurface(rentalRequest.getSurface());
    rental.setPrice(rentalRequest.getPrice());
    rental.setPicture(imageUrl);
    rental.setDescription(rentalRequest.getDescription());
    rental.setOwner(owner);
    rental.setCreatedAt(LocalDateTime.now());
    rental.setUpdatedAt(LocalDateTime.now());

    return rentalRepository.save(rental);
  }



//  public Rental updateRental(Rental rental){
//    return rentalRepository.save(rental);
//  }

}
