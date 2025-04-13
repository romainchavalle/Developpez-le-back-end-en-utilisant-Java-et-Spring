package com.rentals.house.service.impl;


import com.rentals.house.dto.MessageRequest;
import com.rentals.house.model.Message;
import com.rentals.house.model.Rental;
import com.rentals.house.model.User;
import com.rentals.house.repository.MessageRepository;
import com.rentals.house.repository.RentalRepository;
import com.rentals.house.repository.UserRepository;
import com.rentals.house.service.MessageService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

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

  public Message saveMessage (MessageRequest message) {

    Message messageToSave = new Message();
    messageToSave.setMessage(message.getMessage());

    User user = this.userRepository.findById(message.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
    Rental rental = this.rentalRepository.findById(message.getRental_id()).orElseThrow(() -> new RuntimeException("Rental not found"));

    messageToSave.setUser(user);
    messageToSave.setRental(rental);

    messageToSave.setCreated_at(LocalDate.now());
    messageToSave.setUpdated_at(LocalDate.now());

    return this.messageRepository.save(messageToSave);
  }

}
