package com.rentals.house.service;

import com.rentals.house.dto.RentalRequest;
import com.rentals.house.dto.RentalResponse;
import com.rentals.house.dto.UserDto;
import com.rentals.house.model.Rental;
import com.rentals.house.model.User;
import com.rentals.house.repository.RentalRepository;
import com.rentals.house.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RentalService {

  public List<RentalResponse> getAllRentals();

  public Optional<RentalResponse> getRentalById(Long id);

  public Rental saveRental(RentalRequest rental);

  public void updateRental(Long id, RentalRequest rental);

}
