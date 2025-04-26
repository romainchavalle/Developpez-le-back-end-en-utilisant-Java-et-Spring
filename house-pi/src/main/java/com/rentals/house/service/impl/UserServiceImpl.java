package com.rentals.house.service.impl;

import com.rentals.house.dto.UserDto;
import com.rentals.house.exception.EntityNotFoundException;
import com.rentals.house.service.JWTService;
import com.rentals.house.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.rentals.house.model.User;
import com.rentals.house.repository.UserRepository;
import com.rentals.house.dto.RegisterRequest;

import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JWTService jwtService;
  private final ModelMapper modelMapper;

  // FIND A USER BY ID
  public UserDto getUserById(Long id) {
    User user = this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

    // The user is return by a DTO so the password can stay secret (not include in the DTO)
    return modelMapper.map(user, UserDto.class);
  }

  // REGISTER A NEW USER
  public ResponseEntity<Map<String, String>> register(RegisterRequest request){

    // First, we need to check if the email already exist in database
    Optional<User> optionalUser = this.userRepository.findByEmail(request.getEmail());

    if (optionalUser.isPresent()) {
      throw new RuntimeException("This email is already used");
    }

    // Then we can create a new user with de data given by the DTO RegisterRequest
    User user = modelMapper.map(request, User.class);

    // Do not forget the encode the password so it can't be seen from the database
    String cryptPassword = this.passwordEncoder.encode(request.getPassword());
    user.setPassword(cryptPassword);

    this.userRepository.save(user);

    // After user is created, we can return the jwt (allow http request protected to success)
    String jwtToken = this.jwtService.generateToken(request.getEmail());

    return ResponseEntity.ok(Map.of("token", jwtToken));
  }

  // LOGIN AN EXISTING USER (WITH EMAIL + PASSWORD)
  public String login(String email, String password) {

    // First, we need to check if the user exist with the email given in argument
    User user = this.userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));

    // Then, we can check if the password in database correspond to the password given by the login form
    if (!this.passwordEncoder.matches(password, user.getPassword())){
      throw new RuntimeException("Wrong password");
    };

    // It does match ? -> return the jwt token (allow http request protected to success)
    return jwtService.generateToken(user.getEmail());
  }

  // GET THE USER CONNECTED FROM JWT TOKEN
  public UserDto getConnectedUser() {

    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    String email = jwt.getClaim("email");
    User user =  this.userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
    Long userId = user.getId();
    return getUserById(userId);
  }
}
