package com.crealink.app.services.impls;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crealink.app.dto.response.ResponseStatus;
import com.crealink.app.dto.user.UserDto;
import com.crealink.app.entities.User;
import com.crealink.app.enums.SystemStatus;
import com.crealink.app.exceptions.ResourceAlreadyExistsException;
import com.crealink.app.mapper.UserMapper;
import com.crealink.app.repositories.UserRepository;
import com.crealink.app.services.UserService;
import com.crealink.app.utilities.PasswordUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByExternalId(String externalId, SystemStatus status) {
      return userRepository.findByExternalIdAndSystemStatus(UUID.fromString(externalId), status);
    }

    @Override
    public UserDto createUser(UserDto user, PasswordEncoder passwordEncoder) {
      if (existsByEmail(user.email())) {
        throw new ResourceAlreadyExistsException(ResponseStatus.EMAIL_ALREADY_EXISTS);
      }
      if (userRepository.existsByUsername(user.username())) {
        throw new ResourceAlreadyExistsException(ResponseStatus.USERNAME_EXISTS);
      }
      
      User newUser = userMapper.toEntity(user);

      String[] password = PasswordUtil.generatePasswordHash(passwordEncoder, user.password());
      String salt = password[0];
      String passwordHashed = password[1];

      newUser.setPasswordHashed(passwordHashed);
      newUser.setPasswordSalt(salt);
      newUser.setSystemStatus(SystemStatus.ACTIVE);

      newUser = userRepository.save(newUser);
      return userMapper.toDto(newUser);
    }
    
    @Override
    public Optional<User> findByEmailOrUsername(String username) {
        return userRepository.findByEmailOrUsername(username, username, SystemStatus.ACTIVE);
    }

    @Override
    public Boolean checkUsernameAvailability(String username) {
      boolean isExisting = userRepository.existsByUsername(username);
      return !isExisting;
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}