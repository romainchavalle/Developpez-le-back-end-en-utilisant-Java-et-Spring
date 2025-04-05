package com.rentals.house.service;

import com.rentals.house.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import org.springframework.web.server.ResponseStatusException;


import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JWTService jwtService;

  public UserDto getUserById(Long id) {
    User user = this.userRepository.findById(id).orElse(null);
    return new UserDto(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
  }

  public ResponseEntity<Map<String, String>> register(RegisterRequest request){
    // VERIFIER SI LE USER EXISTE DEJA AVANT DE CREER
    Optional<User> optionalUser = this.userRepository.findByEmail(request.getEmail());

    if (optionalUser.isPresent()) {
      throw new RuntimeException("This email is already used");
    }

    // CREATION USER + ENCRYPTER MDP
    User user = new User();
    user.setEmail(request.getEmail());
    String cryptPassword = this.passwordEncoder.encode(request.getPassword());
    user.setPassword(cryptPassword);
    user.setName(request.getName());
    this.userRepository.save(user);

    String jwtToken = this.jwtService.generateToken(request.getEmail());

    return ResponseEntity.ok(Map.of("jwtToken", jwtToken));
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
    // To get connected user, we use the subject of the jwt token. It contains the email of the user
    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    String email = jwt.getClaim("email");
    // When user is not found, we throw an exception which will be caught by exception handler
    Long userId = this.userRepository.findByEmail(email).get().getId();
    return getUserById(userId);
  }
}
