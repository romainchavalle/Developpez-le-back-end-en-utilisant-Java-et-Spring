package com.rentals.house.service.impl;

import com.rentals.house.dto.UserDto;
import com.rentals.house.service.JWTService;
import com.rentals.house.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.rentals.house.model.User;
import com.rentals.house.repository.UserRepository;
import com.rentals.house.dto.RegisterRequest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JWTService jwtService;

  // FIND A USER BY ID
  public UserDto getUserById(Long id) {
    User user = this.userRepository.findById(id).orElse(null);

    // The user is return by a DTO so the password can stay secret (not include in the DTO)
    return new UserDto(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
  }

  // REGISTER A NEW USER
  public ResponseEntity<Map<String, String>> register(RegisterRequest request){

    // First, we need to check if the email already exist in database
    Optional<User> optionalUser = this.userRepository.findByEmail(request.getEmail());

    if (optionalUser.isPresent()) {
      throw new RuntimeException("This email is already used");
    }

    // Then we can create a new user with de data given by the DTO RegisterRequest
    User user = new User();
    user.setEmail(request.getEmail());
    user.setName(request.getName());
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());

    // Do not forget the encode the password so it can't be seen from the database
    String cryptPassword = this.passwordEncoder.encode(request.getPassword());
    user.setPassword(cryptPassword);

    this.userRepository.save(user);

    // After user is created, we can return the jwt (allow http request protected to success)
    String jwtToken = this.jwtService.generateToken(request.getEmail());

    return ResponseEntity.ok(Map.of("token", jwtToken));
  }

  // LOGIN AN EXISTING USER (WITH EMAIL + PASSWORD)
  public Optional<String> login(String email, String password) {

    // First, we need to check if the user exist with the email given in argument
    User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Then, we can check if the password in database correspond to the password given by the login form
    if (!this.passwordEncoder.matches(password, user.getPassword())){
      return Optional.empty();
    };

    // It does match ? -> return the jwt token (allow http request protected to success)
    String jwtToken = jwtService.generateToken(user.getEmail());
    return Optional.of(jwtToken);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    // The username used for authentication must be the email
    return this.userRepository
      .findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("No user found with this email"));
  }

  // GET THE USER CONNECTED FROM JWT TOKEN
  public UserDto getConnectedUser() {

    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    String email = jwt.getClaim("email");
    Long userId = this.userRepository.findByEmail(email).get().getId();
    return getUserById(userId);
  }
}
