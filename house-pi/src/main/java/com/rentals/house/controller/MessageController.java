package com.rentals.house.controller;

import com.rentals.house.dto.MessageRequest;
import com.rentals.house.dto.MessageResponse;
import com.rentals.house.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Message CRUD", description = "This API give you the CRUD of the message.")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  // POST A MESSAGE FOR A RENTAL
  @Operation(summary = "Create a new message", description = "The message will be linked to his user and his rental.")
  @PostMapping
  public ResponseEntity<MessageResponse> saveMessage(@RequestBody MessageRequest message){
    this.messageService.saveMessage(message);
    MessageResponse messageResponse = new MessageResponse("Message sent with success");
    return ResponseEntity.ok(messageResponse);
  }
}
