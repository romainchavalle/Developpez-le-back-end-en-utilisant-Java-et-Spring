package com.rentals.house.exception;

public class EntityNotSavedException extends RuntimeException {
  public EntityNotSavedException(String message) {
    super(message);
  }
}
