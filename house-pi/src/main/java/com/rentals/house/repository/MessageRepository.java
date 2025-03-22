package com.rentals.house.repository;

import com.rentals.house.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  // List<Message> findByRentalId(Long rentalId);
  // List<Message> findByUserId(Long userId);
}
