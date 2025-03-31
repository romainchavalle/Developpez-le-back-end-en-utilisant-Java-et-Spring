package com.rentals.house.controller;

import com.rentals.house.dto.AuthenticationDTO;
import com.rentals.house.model.User;
import com.rentals.house.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  @PostMapping("/register")
  public void register(@RequestBody User user) {
    this.userService.register(user);
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO) {
    final Authentication authenticate = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password())
    );
    return null;
  }
}
