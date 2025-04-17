package com.rentals.house.service;

import com.rentals.house.dto.UserDto;
import org.springframework.http.ResponseEntity;
import com.rentals.house.dto.RegisterRequest;
import java.util.Map;
import java.util.Optional;


public interface UserService {

  UserDto getUserById(Long id);
  ResponseEntity<Map<String, String>> register(RegisterRequest request);
  Optional<String> login(String email, String password);
  UserDto getConnectedUser();

}
