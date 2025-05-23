package com.rentals.house.controller;

import com.rentals.house.dto.JwtResponse;
import com.rentals.house.dto.LoginRequest;
import com.rentals.house.dto.RegisterRequest;
import com.rentals.house.dto.UserDto;
import com.rentals.house.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication's endpoints", description = "This API give you the different endpoints for connected user")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  // CREATE A NEW ACCOUNT ON THE APP
  @PostMapping("/register")
  public ResponseEntity<JwtResponse>  register(@RequestBody RegisterRequest registerRequest) {
    // This function create a new user et return the jwt token
    String jwtToken = this.userService.register(registerRequest);
    JwtResponse jwtResponse = new JwtResponse();
    jwtResponse.setToken(jwtToken);
    return ResponseEntity.ok(jwtResponse);
  }

  // LOGIN FROM EXISTING ACCOUNT (EMAIL + PASSWORD)
  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
    // This function check the different credentials and return the jwt token
    String jwtToken = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    JwtResponse jwtResponse = new JwtResponse();
    jwtResponse.setToken(jwtToken);
    return ResponseEntity.ok(jwtResponse);
  }

  // GET INFOS FROM THE CURRENT USER
  @Operation(summary = "Get the user's attributes", description = "Only if the user is connected.")
  @GetMapping("/me")
  public ResponseEntity<UserDto> getConnectedUser() {
    UserDto user = this.userService.getConnectedUser();
    return ResponseEntity.ok(user);
  }
}
