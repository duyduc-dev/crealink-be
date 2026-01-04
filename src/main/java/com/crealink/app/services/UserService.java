package com.crealink.app.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.crealink.app.dto.user.UserDto;
import com.crealink.app.entities.User;
import com.crealink.app.enums.SystemStatus;

public interface UserService {

  /**
   * Find user by external id and status
   * @param externalId
   * @param status
   * @return
   */
  Optional<User> findByExternalId(String externalId, SystemStatus status);

  /**
   * Create a new user
   * @param user
   * @param passwordEncoder
   * @return
   */
  UserDto createUser(UserDto user, PasswordEncoder passwordEncoder);

  /**
   * Find user by email or username
   * @param username
   * @return Optional<User>
   */
  Optional<User> findByEmailOrUsername(String username);
}
