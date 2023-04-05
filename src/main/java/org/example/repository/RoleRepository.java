package org.example.repository;

import org.example.entities.Entities_Realy.Auth.RoleEntity;
import org.example.entities.Entities_Realy.Auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<RoleEntity,Integer> {
    RoleEntity findByName(String Name);
}
