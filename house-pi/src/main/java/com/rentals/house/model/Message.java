package com.rentals.house.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "messages")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "rental_id", nullable = false)
  private Rental rental;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private String message;
  @Column(name = "created_at", updatable = false)
  private LocalDate created_at;

  @Column(name = "updated_at")
  private LocalDate updated_at;

}
