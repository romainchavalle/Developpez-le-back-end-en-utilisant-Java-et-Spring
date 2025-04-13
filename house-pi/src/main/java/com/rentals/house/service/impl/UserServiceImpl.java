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

  public UserDto getUserById(Long id) {
    User user = this.userRepository.findById(id).orElse(null);
    return new UserDto(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
  }

  public ResponseEntity<Map<String, String>> register(RegisterRequest request){

    Optional<User> optionalUser = this.userRepository.findByEmail(request.getEmail());

    if (optionalUser.isPresent()) {
      throw new RuntimeException("This email is already used");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    String cryptPassword = this.passwordEncoder.encode(request.getPassword());
    user.setPassword(cryptPassword);
    user.setName(request.getName());
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());
    this.userRepository.save(user);

    String jwtToken = this.jwtService.generateToken(request.getEmail());

    return ResponseEntity.ok(Map.of("token", jwtToken));
  }

  public Optional<String> login(String email, String password) {
    User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    if (!this.passwordEncoder.matches(password, user.getPassword())){
      return Optional.empty();
    };

    String jwtToken = jwtService.generateToken(user.getEmail());
    return Optional.of(jwtToken);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.userRepository
      .findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("No user found with this email"));
  }

  public UserDto getConnectedUser() {

    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    String email = jwt.getClaim("email");
    Long userId = this.userRepository.findByEmail(email).get().getId();
    return getUserById(userId);
  }
}
