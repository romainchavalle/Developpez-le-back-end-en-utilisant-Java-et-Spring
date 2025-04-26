package com.rentals.house.service.impl;


import com.rentals.house.dto.MessageRequest;
import com.rentals.house.exception.EntityNotFoundException;
import com.rentals.house.exception.EntityNotSavedException;
import com.rentals.house.model.Message;
import com.rentals.house.model.Rental;
import com.rentals.house.model.User;
import com.rentals.house.repository.MessageRepository;
import com.rentals.house.repository.RentalRepository;
import com.rentals.house.repository.UserRepository;
import com.rentals.house.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final RentalRepository rentalRepository;

  public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, RentalRepository rentalRepository) {
    this.messageRepository = messageRepository;
    this.userRepository = userRepository;
    this.rentalRepository = rentalRepository;
  }

  // SAVE MESSAGE WHEN CREATED
  public void saveMessage (MessageRequest message) {
    try {
      // Set the different columns of the message
      Message messageToSave = new Message();
      messageToSave.setMessage(message.getMessage());

      // Set the different foreign keys
      User user = this.userRepository.findById(message.getUser_id()).orElseThrow(() -> new EntityNotFoundException("User not found"));
      Rental rental = this.rentalRepository.findById(message.getRental_id()).orElseThrow(() -> new EntityNotFoundException("Rental not found"));
      messageToSave.setUser(user);
      messageToSave.setRental(rental);

      this.messageRepository.save(messageToSave);
    } catch (Exception e) {
      throw new EntityNotSavedException("Message not saved!");
    }
  }
}
