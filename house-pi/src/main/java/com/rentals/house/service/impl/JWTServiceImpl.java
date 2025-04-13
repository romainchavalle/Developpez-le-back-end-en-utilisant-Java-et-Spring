package com.rentals.house.service.impl;

import com.rentals.house.service.JWTService;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

@Service
public class JWTServiceImpl implements JWTService {

  private final JwtEncoder jwtEncoder;

  public JWTServiceImpl(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  // GENERATE JWT FOR LOGIN AND REGISTER REQUESTS
  public String generateToken(String email) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plus(1, ChronoUnit.DAYS))
      .claim("email", email)
      .build();
    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
    return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }

}
