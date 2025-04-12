package com.rentals.house.service;

import com.rentals.house.dto.RentalRequest;
import com.rentals.house.dto.UserDto;
import com.rentals.house.model.Rental;
import com.rentals.house.model.User;
import com.rentals.house.repository.RentalRepository;
import com.rentals.house.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

  // déclaration objet
  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  // contructeur (peut-être remplacé par autowired)
  public RentalService(RentalRepository rentalRepository, UserRepository userRepository,UserService userService) {
    this.userRepository = userRepository;
    this.rentalRepository = rentalRepository;
    this.userService = userService;
  }

  // Méthodes (ces methods existent grâce à JPA dans le repo)
  public List<Rental> getAllRentals() {
    return rentalRepository.findAll();
  }

  public Optional<Rental> getRentalById(Long id) {
    return rentalRepository.findById(id);
  }

  public Rental saveRental(RentalRequest rental) {

    Rental rentalToSave = new Rental();
    rentalToSave.setName(rental.getName());
    rentalToSave.setPrice(rental.getPrice());
    rentalToSave.setDescription(rental.getDescription());
    rentalToSave.setSurface(rental.getSurface());

    rentalToSave.setPicture(rental.getPicture());
    rentalToSave.setCreatedAt(LocalDate.now());
    rentalToSave.setUpdatedAt(LocalDate.now());

    UserDto connectedUser = this.userService.getConnectedUser();
    Optional<User> owner = userRepository.findById(connectedUser.getId());
    rentalToSave.setOwner(owner.get());

    return rentalRepository.save(rentalToSave);
  }



//  public Rental updateRental(Rental rental){
//    return rentalRepository.save(rental);
//  }

}
