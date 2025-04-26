package com.rentals.house.controller;

import com.rentals.house.dto.UserDto;
import com.rentals.house.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User CRUD", description = "This API give you the CRUD of the user.")
@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
    UserDto user = this.userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

}
