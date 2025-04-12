package com.rentals.house.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rentals")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name" ,nullable = false)
  private String name;
  private Double surface;
  private Double price;
  private String picture;
  private String description;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  private LocalDate createdAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  private LocalDate updatedAt;

}
