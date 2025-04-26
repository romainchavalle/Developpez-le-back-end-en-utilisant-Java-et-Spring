package com.rentals.house.service;

import com.rentals.house.dto.UserDto;
import com.rentals.house.dto.RegisterRequest;


public interface UserService {

  UserDto getUserById(Long id);
  String register(RegisterRequest request);
  String login(String email, String password);
  UserDto getConnectedUser();

}
