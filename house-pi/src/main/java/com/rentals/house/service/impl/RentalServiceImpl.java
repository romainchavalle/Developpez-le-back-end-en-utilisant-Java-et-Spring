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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

  private final FileStorageService fileStorageService;
  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;
  private final UserService userService;
  private final ModelMapper modelMapper;

  public RentalServiceImpl(RentalRepository rentalRepository, UserRepository userRepository,UserService userService, FileStorageService fileStorageService,ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.rentalRepository = rentalRepository;
    this.userService = userService;
    this.fileStorageService = fileStorageService;
    this.modelMapper = modelMapper;
  }

  // GIVE ALL THE RENTALS
  public List<RentalResponse> getAllRentals() {
    List<Rental> rentals = rentalRepository.findAll();
    List<RentalResponse> rentalResponses = new ArrayList<>();

    // Map the DTO with the picture url dynamically (picture url is used in front-end to get the picture from stored directory)
    for (Rental rental : rentals) {
      RentalResponse rentalResponse = modelMapper.map(rental, RentalResponse.class);
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

      // Map the DTO with the picture url dynamically (picture url is used in front-end to get the picture from stored directory)
      RentalResponse rentalResponse = modelMapper.map(rental, RentalResponse.class);
      rentalResponse.setOwner_id(rental.getOwner().getId());
      rentalResponse.setPicture("/api/pictures" + rental.getPicture());

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
    Rental rentalToSave = modelMapper.map(rental, Rental.class);
    rentalToSave.setPicture(imageUrl);

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
    modelMapper.map(rental, rentalToUpdate);

    rentalRepository.save(rentalToUpdate);
  }
}
