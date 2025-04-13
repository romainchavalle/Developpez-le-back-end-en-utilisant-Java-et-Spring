package com.rentals.house.service.impl;

import com.rentals.house.dto.RentalRequest;
import com.rentals.house.dto.UserDto;
import com.rentals.house.model.Rental;
import com.rentals.house.model.User;
import com.rentals.house.repository.RentalRepository;
import com.rentals.house.repository.UserRepository;
import com.rentals.house.service.RentalService;
import com.rentals.house.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  public RentalServiceImpl(RentalRepository rentalRepository, UserRepository userRepository,UserService userService) {
    this.userRepository = userRepository;
    this.rentalRepository = rentalRepository;
    this.userService = userService;
  }

  public List<RentalRequest> getAllRentals() {
    List<Rental> rentals = rentalRepository.findAll();
    List<RentalRequest> rentalRequests = new ArrayList<>();

    for (Rental rental : rentals) {
      RentalRequest rentalRequest = new RentalRequest();
      rentalRequest.setId(rental.getId());
      rentalRequest.setName(rental.getName());
      rentalRequest.setSurface(rental.getSurface());
      rentalRequest.setPrice(rental.getPrice());
      rentalRequest.setDescription(rental.getDescription());
      rentalRequest.setOwner_id(rental.getOwner().getId());
      rentalRequest.setPicture("/api/pictures" + rental.getPicture());

      rentalRequests.add(rentalRequest);
    }

    return rentalRequests;
  }

  public Optional<RentalRequest> getRentalById(Long id) {
    Optional<Rental> rentalOptional = rentalRepository.findById(id);
    if (rentalOptional.isPresent()) {
      Rental rental = rentalOptional.get();
      RentalRequest rentalRequest = new RentalRequest();

      rentalRequest.setId(rental.getId());
      rentalRequest.setName(rental.getName());
      rentalRequest.setDescription(rental.getDescription());
      rentalRequest.setPrice(rental.getPrice());
      rentalRequest.setOwner_id(rental.getOwner().getId());
      rentalRequest.setSurface(rental.getSurface());
      rentalRequest.setPicture("/api/pictures" + rental.getPicture());
      rentalRequest.setUpdated_at(rental.getUpdatedAt());
      rentalRequest.setCreated_at(rental.getCreatedAt());

      return Optional.of(rentalRequest);
    } else {
      return Optional.empty();
    }

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

  public void updateRental(Long id, RentalRequest rental) {
    Optional<Rental> rentalOptional = rentalRepository.findById(id);

    if (rentalOptional.isEmpty()) {
      throw new IllegalArgumentException("Rental not found");
    }

    Rental rentalToUpdate = rentalOptional.get();

    rentalToUpdate.setName(rental.getName());
    rentalToUpdate.setSurface(rental.getSurface());
    rentalToUpdate.setPrice(rental.getPrice());
    rentalToUpdate.setDescription(rental.getDescription());
    rentalToUpdate.setUpdatedAt(LocalDate.now());

    rentalRepository.save(rentalToUpdate);
  }
}
