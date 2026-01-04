package com.crealink.app.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.crealink.app.dto.user.UserDto;
import com.crealink.app.entities.User;

@Component
public class UserMapper {
    
    /**
     * Convert User entity to UserDto
     * @param user
     * @return
     */
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getExternalId().toString())
                .internalId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .password(user.getPasswordHashed())
                .passwordSalt(user.getPasswordSalt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    
    /**
     * Convert UserDto to User entity
     * @param userDto
     * @return
     */
    public User toEntity(UserDto userDto) {
        UUID externalId = userDto.id() != null ? UUID.fromString(userDto.id()) : null;
        return User.builder()
            .id(userDto.internalId())
            .externalId(externalId)
            .firstName(userDto.firstName())
            .lastName(userDto.lastName())
            .email(userDto.email())
            .updatedAt(userDto.updatedAt())
            .createdAt(userDto.createdAt())
            .dateOfBirth(userDto.dateOfBirth())
            .username(userDto.username())
            .build();
    }
}
