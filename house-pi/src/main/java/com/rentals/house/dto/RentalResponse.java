package com.rentals.house.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.Instant;

@Data
public class RentalResponse {
  private Long id;
  private String name;
  private Double surface;
  private Double price;
  private String picture;
  private String description;
  private Long owner_id;

  @JsonProperty("created_at")
  Instant createdAt;

  @JsonProperty("updated_at")
  Instant updatedAt;
}
