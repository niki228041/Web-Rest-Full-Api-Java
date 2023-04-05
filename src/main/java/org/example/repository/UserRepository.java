package org.example.repository;

import org.example.entities.Entities_Realy.Auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmail(String email);
}
