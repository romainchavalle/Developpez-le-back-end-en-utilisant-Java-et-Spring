package com.rentals.house.controller;

import com.rentals.house.model.Message;
import com.rentals.house.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @PostMapping
  public Message createMessage(@RequestBody Message message) {
    return messageService.saveMessage(message);
  }
}
