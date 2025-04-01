package com.rentals.house.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.rentals.house.model.User;
import com.rentals.house.repository.UserRepository;
import com.rentals.house.dto.RegisterRequest;


import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;


  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }


  public void register(RegisterRequest request){
    // VERIFIER SI LE USER EXISTE DEJA AVANT DE CREER
    Optional<User> optionalUser = this.userRepository.findByEmail(request.getEmail());

    if (optionalUser.isPresent()) {
      throw new RuntimeException("This email is already used");
    }

    // CREATION USER + ENCRYPTER MDP
    User user = new User();
    user.setEmail(request.getEmail());
    String cryptPassword = this.passwordEncoder.encode(request.getPassword());
    user.setPassword(cryptPassword);
    user.setName(request.getName());
    this.userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.userRepository
      .findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("No user found with this email"));
  }
}
