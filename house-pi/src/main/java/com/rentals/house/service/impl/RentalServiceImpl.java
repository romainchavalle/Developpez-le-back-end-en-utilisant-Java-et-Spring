package com.rentals.house.service.impl;

import com.rentals.house.dto.RentalRequest;
import com.rentals.house.dto.RentalResponse;
import com.rentals.house.dto.UserDto;
import com.rentals.house.model.Rental;
import com.rentals.house.model.User;
import com.rentals.house.repository.RentalRepository;
import com.rentals.house.repository.UserRepository;
import com.rentals.house.service.FileStorageService;
import com.rentals.house.service.RentalService;
import com.rentals.house.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

  private final FileStorageService fileStorageService;
  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  public RentalServiceImpl(RentalRepository rentalRepository, UserRepository userRepository,UserService userService, FileStorageService fileStorageService) {
    this.userRepository = userRepository;
    this.rentalRepository = rentalRepository;
    this.userService = userService;
    this.fileStorageService = fileStorageService;
  }

  // GIVE ALL THE RENTALS
  public List<RentalResponse> getAllRentals() {
    List<Rental> rentals = rentalRepository.findAll();
    List<RentalResponse> rentalResponses = new ArrayList<>();

    // Map the DTO with the picture url dynamically (picture url is used in front-end to get the picture from stored directory)
    for (Rental rental : rentals) {
      RentalResponse rentalResponse = new RentalResponse();
      rentalResponse.setId(rental.getId());
      rentalResponse.setName(rental.getName());
      rentalResponse.setSurface(rental.getSurface());
      rentalResponse.setPrice(rental.getPrice());
      rentalResponse.setDescription(rental.getDescription());
      rentalResponse.setOwner_id(rental.getOwner().getId());
      rentalResponse.setPicture("/api/pictures" + rental.getPicture());

      rentalResponses.add(rentalResponse);
    }

    return rentalResponses;
  }

  // GIVE 1 RENTAL BY ID
  public Optional<RentalResponse> getRentalById(Long id) {
    Optional<Rental> rentalOptional = rentalRepository.findById(id);
    if (rentalOptional.isPresent()) {
      Rental rental = rentalOptional.get();
      RentalResponse rentalResponse = new RentalResponse();

      // Map the DTO with the picture url dynamically (picture url is used in front-end to get the picture from stored directory)
      rentalResponse.setId(rental.getId());
      rentalResponse.setName(rental.getName());
      rentalResponse.setDescription(rental.getDescription());
      rentalResponse.setPrice(rental.getPrice());
      rentalResponse.setOwner_id(rental.getOwner().getId());
      rentalResponse.setSurface(rental.getSurface());
      rentalResponse.setPicture("/api/pictures" + rental.getPicture());
      rentalResponse.setUpdated_at(rental.getUpdatedAt());
      rentalResponse.setCreated_at(rental.getCreatedAt());

      return Optional.of(rentalResponse);

    } else {
      return Optional.empty();
    }

  }

  // SAVE A RENTAL
  public Rental saveRental(RentalRequest rental) {

    // Store the file in the 'picture' directory and return the url to call to access to that file
    String imageUrl = fileStorageService.storeFile(rental.getPicture());

    // Map the rental with the data received by the DTO RentalRequest
    Rental rentalToSave = new Rental();
    rentalToSave.setName(rental.getName());
    rentalToSave.setPrice(rental.getPrice());
    rentalToSave.setDescription(rental.getDescription());
    rentalToSave.setSurface(rental.getSurface());
    rentalToSave.setPicture(imageUrl);
    rentalToSave.setCreatedAt(LocalDate.now());
    rentalToSave.setUpdatedAt(LocalDate.now());

    // As the connectedUser is creating the rental, it corresponds to the owner of the rental
    UserDto connectedUser = this.userService.getConnectedUser();
    Optional<User> owner = userRepository.findById(connectedUser.getId());
    rentalToSave.setOwner(owner.get());

    return rentalRepository.save(rentalToSave);
  }

  // UPDATE A RENTAL BY ID
  public void updateRental(Long id, RentalRequest rental) {
    Optional<Rental> rentalOptional = rentalRepository.findById(id);

    if (rentalOptional.isEmpty()) {
      throw new IllegalArgumentException("Rental not found");
    }

    Rental rentalToUpdate = rentalOptional.get();

    // Map the new content to the rentalToUpdate which has been found with id in argument
    rentalToUpdate.setName(rental.getName());
    rentalToUpdate.setSurface(rental.getSurface());
    rentalToUpdate.setPrice(rental.getPrice());
    rentalToUpdate.setDescription(rental.getDescription());
    rentalToUpdate.setUpdatedAt(LocalDate.now());

    rentalRepository.save(rentalToUpdate);
  }
}
