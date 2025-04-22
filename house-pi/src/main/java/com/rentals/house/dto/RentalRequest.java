package com.rentals.house.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalRequest {
  private Long id;
  private String name;
  private Double surface;
  private Double price;
  private MultipartFile picture;
  private String description;
  private Long owner_id;
}
