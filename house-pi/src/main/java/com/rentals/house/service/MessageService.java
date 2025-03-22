package com.rentals.house.service;

import com.rentals.house.model.Message;
import com.rentals.house.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public Message saveMessage (Message message) {
    return messageRepository.save(message);
  }

}
