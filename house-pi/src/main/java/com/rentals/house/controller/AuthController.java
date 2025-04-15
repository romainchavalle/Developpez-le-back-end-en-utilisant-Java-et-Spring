package com.rentals.house.controller;

import com.rentals.house.dto.LoginRequest;
import com.rentals.house.dto.RegisterRequest;
import com.rentals.house.dto.UserDto;
import com.rentals.house.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Tag(name = "Authentication's endpoints", description = "This API give you the different endpoints for connected user")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  // CREATE A NEW ACCOUNT ON THE APP
  @Hidden
  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest registerRequest) {
    // This function create a new user et return the jwt token
    return this.userService.register(registerRequest);
  }

  // LOGIN FROM EXISTING ACCOUNT (EMAIL + PASSWORD)
  @Hidden
  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
    // This function check the different credentials and return the jwt token
    Optional<String> jwtToken = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());

    if (jwtToken.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("message", "Invalid email or password"));
    }

    return ResponseEntity.ok(Map.of("token", jwtToken.get()));
  }

  // GET INFOS FROM THE CURRENT USER
  @Operation(summary = "Get the user's attributes", description = "Only if the user is connected.")
  @GetMapping("/me")
  public ResponseEntity<UserDto> getConnectedUser() {
    UserDto user = this.userService.getConnectedUser();

    if (user == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    return ResponseEntity.ok(user);
  }
}
