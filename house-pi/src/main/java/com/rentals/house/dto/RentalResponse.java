package com.rentals.house.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class RentalResponse {
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
