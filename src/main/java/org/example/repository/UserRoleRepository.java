package org.example.repository;

import org.example.entities.Entities_Realy.Auth.UserEntity;
import org.example.entities.Entities_Realy.Auth.UserRoleEntity;
import org.example.entities.Entities_Realy.Auth.UserRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRolePK> {
    List<UserRoleEntity> findByUser(UserEntity User);
}