package org.example.repository;


import org.example.entities.Entities_Realy.UserAvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvatarImageRepository extends JpaRepository<UserAvatarEntity,Integer> {

}
