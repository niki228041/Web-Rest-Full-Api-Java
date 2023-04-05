package org.example.services;


import lombok.AllArgsConstructor;
import org.example.constant.Roles;
import org.example.entities.Entities_Realy.Auth.RoleEntity;
import org.example.entities.Entities_Realy.Auth.UserEntity;
import org.example.interfaces.SeedService;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeedServiceImpl implements SeedService {

    RoleRepository roleRepository;

    @Override
    public void seedRoleData(){
        if(roleRepository.count()==0)
        {
            RoleEntity admin = new RoleEntity().builder()
                    .name(Roles.Admin)
                    .build();
            roleRepository.save(admin);

            RoleEntity user = new RoleEntity().builder()
                    .name(Roles.User)
                    .build();

            roleRepository.save(user);
        }
    }

    @Override
    public void seedUserData(){
        var user = new UserEntity().builder()
                .email("admin@gmail.com")
                .firstName("Alex")
                .lastName("Prostitut")
                .phone("+49 14928319444")
                .build();

    }
}
