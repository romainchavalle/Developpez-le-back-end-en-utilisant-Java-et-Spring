package com.rentals.house.exception;

import com.rentals.house.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({EntityNotFoundException.class})
  public ResponseEntity<Object> EntityNotFoundException(EntityNotFoundException exception) {
    MessageResponse messageResponse = new MessageResponse();
    messageResponse.setMessage(exception.getMessage());

    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(messageResponse);
  }

  @ExceptionHandler({EntityNotSavedException.class})
  public ResponseEntity<Object> EntityNotSavedException(EntityNotSavedException exception) {
    MessageResponse messageResponse = new MessageResponse();
    messageResponse.setMessage(exception.getMessage());

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(messageResponse);
  }


  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
    MessageResponse messageResponse = new MessageResponse();
    messageResponse.setMessage(exception.getMessage());

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(messageResponse);
  }
}
