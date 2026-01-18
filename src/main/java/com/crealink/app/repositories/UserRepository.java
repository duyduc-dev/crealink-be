package com.crealink.app.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crealink.app.entities.User;
import com.crealink.app.enums.SystemStatus;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByExternalIdAndSystemStatus(UUID externalId, SystemStatus systemStatus);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE (u.email = :email OR u.username = :username) AND u.systemStatus = :systemStatus")
    Optional<User> findByEmailOrUsername(
        @Param("email") String email, 
        @Param("username") String username,
        @Param("systemStatus") SystemStatus systemStatus
    );

}
