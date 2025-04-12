package com.rentals.house.dto;

import lombok.Data;

@Data
public class RentalRequest {
  private String name;
  private Double surface;
  private Double price;
  private String picture;
  private String description;
  private Long ownerId;
}
