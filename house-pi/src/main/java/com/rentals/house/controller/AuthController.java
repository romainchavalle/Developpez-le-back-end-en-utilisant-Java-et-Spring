package com.rentals.house.controller;

import com.rentals.house.dto.AuthenticationDTO;
import com.rentals.house.dto.RegisterRequest;
import com.rentals.house.model.User;
import com.rentals.house.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.rentals.house.service.JWTService;


import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JWTService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    this.userService.register(request);

    String jwtToken = this.jwtService.generateToken(request.getEmail());

    // REPRENDRE ICI PUIS TESTER, PUIS LOGIN, PUIS /ME, PUIS VOIR TOUTES LES AUTRES ROUTES
    return ResponseEntity.ok(new AuthResponse(jwtToken));
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO) {
    final Authentication authenticate = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password())
    );
    return null;
  }
}
