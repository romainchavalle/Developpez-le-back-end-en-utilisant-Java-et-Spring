package com.rentals.house.controller;

import com.rentals.house.dto.LoginRequest;
import com.rentals.house.dto.RegisterRequest;
import com.rentals.house.model.User;
import com.rentals.house.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    return this.userService.register(registerRequest);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
    Optional<String> jwtToken = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());

    if (jwtToken.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("message", "Invalid email or password"));
    }

    return ResponseEntity.ok(Map.of("jwtToken", jwtToken.get()));
  }
}
