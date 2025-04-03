package com.rentals.house.controller;

import com.rentals.house.dto.LoginRequest;
import com.rentals.house.dto.RegisterRequest;
import com.rentals.house.model.User;
import com.rentals.house.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rentals.house.service.JWTService;


import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JWTService jwtService;
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest registerRequest) {
    this.userService.register(registerRequest);

    String jwtToken = this.jwtService.generateToken(registerRequest.getEmail());

    return ResponseEntity.ok(Map.of("jwtToken", jwtToken));
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
    Optional<User> user = this.userService.findByEmail(loginRequest.getEmail());

    if (user.isEmpty() || !userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword())) {
      return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
    }

    String jwtToken = this.jwtService.generateToken(user.get().getEmail());
    return ResponseEntity.ok(Map.of("jwtToken", jwtToken));
  }
}
