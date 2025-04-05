package com.rentals.house.controller;

import com.rentals.house.dto.MessageRequest;
import com.rentals.house.model.Message;
import com.rentals.house.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @PostMapping
  public ResponseEntity<Map<String, String>> saveMessage(@RequestBody MessageRequest message){
    if(this.messageService.saveMessage(message) != null) {
      return ResponseEntity.ok(Map.of("message", "Message send with success"));
    }
    else {
      return ResponseEntity.ok(Map.of("error", "Message not send with success"));
    }
  }
}
