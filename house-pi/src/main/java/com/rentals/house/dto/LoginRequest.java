package com.rentals.house.dto;

import lombok.Data;

@Data
public class LoginRequest {
  String email;
  String password;
}
