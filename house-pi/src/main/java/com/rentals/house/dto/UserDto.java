package com.rentals.house.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class UserDto {
  Long id;
  String name;
  String email;

  @JsonProperty("created_at")
  Instant createdAt;

  @JsonProperty("updated_at")
  Instant updatedAt;
}
