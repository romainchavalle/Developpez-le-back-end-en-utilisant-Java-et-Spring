package com.rentals.house.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RentalResponseWrapper {
  private List<RentalResponse> rentals;
}
