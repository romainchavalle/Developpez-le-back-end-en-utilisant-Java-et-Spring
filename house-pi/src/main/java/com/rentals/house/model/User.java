package com.rentals.house.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data // permet d'éviter getter, setter, constructer et autre (assignation bdd -> model)
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String email;
  private String name;
  private String password;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
