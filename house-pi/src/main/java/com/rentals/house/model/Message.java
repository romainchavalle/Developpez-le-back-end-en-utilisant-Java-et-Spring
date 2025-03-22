package com.rentals.house.model;

import com.rentals.house.model.Rental;
import com.rentals.house.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
