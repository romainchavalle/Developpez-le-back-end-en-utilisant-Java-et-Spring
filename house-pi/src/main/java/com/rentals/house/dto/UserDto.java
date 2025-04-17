package com.rentals.house.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDto {
  Long id;
  String name;
  String email;
  LocalDateTime created_at;
  LocalDateTime updated_at;

  public UserDto(Long id, String email, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.created_at = createdAt;
    this.updated_at = updatedAt;
  }
}
