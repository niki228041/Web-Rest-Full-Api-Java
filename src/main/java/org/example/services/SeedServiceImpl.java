package org.example.services;


import lombok.AllArgsConstructor;
import org.example.constant.Roles;
import org.example.entities.Dto.Auth.RegisterDto;
import org.example.entities.Entities_Realy.Auth.RoleEntity;
import org.example.entities.Entities_Realy.Auth.UserEntity;
import org.example.interfaces.SeedService;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeedServiceImpl implements SeedService {

    RoleRepository roleRepository;
    UserRepository userRepository;
    private final AccountService service;

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
        if(userRepository.count()==0) {
            var user = new RegisterDto().builder()
                    .email("admin@gmail.com")
                    .lastname("nolastname")
                    .firstname("noname")
                    .password("1111")
                    .build();

            service.register(user, Roles.Admin);
        }
    }
}
