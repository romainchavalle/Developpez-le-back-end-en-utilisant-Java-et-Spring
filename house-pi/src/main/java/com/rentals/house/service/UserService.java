package com.rentals.house.service;

import org.springframework.stereotype.Service;
import com.rentals.house.model.User;
import com.rentals.house.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }
}
