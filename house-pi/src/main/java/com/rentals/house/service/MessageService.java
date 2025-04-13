package com.rentals.house.service;

import com.rentals.house.dto.MessageRequest;
import com.rentals.house.model.Message;

public interface MessageService {
  public Message saveMessage (MessageRequest message);
}
