package com.rentals.house.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RentalRequest {
  private Long id;
  private String name;
  private Double surface;
  private Double price;
  private String picture;
  private String description;
  private Long owner_id;
  private LocalDate updated_at;
  private LocalDate created_at;
}
