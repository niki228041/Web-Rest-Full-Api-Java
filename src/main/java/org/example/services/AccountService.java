package org.example.services;

import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.example.configuration.captcha.CaptchaSettings;
import org.example.configuration.captcha.GoogleResponse;
import org.example.configuration.security.JwtService;
import org.example.constant.Roles;
import org.example.entities.Dto.Auth.AuthResponseDto;
import org.example.entities.Dto.Auth.GoogleAuthDto;
import org.example.entities.Dto.Auth.LoginDto;
import org.example.entities.Dto.Auth.RegisterDto;
import org.example.entities.Dto.AvatarDTO;
import org.example.entities.Dto.FindByIdDTO;
import org.example.entities.Entities_Realy.Auth.UserEntity;
import org.example.entities.Entities_Realy.Auth.UserRoleEntity;
import org.example.entities.Entities_Realy.UserAvatarEntity;
import org.example.repository.RoleRepository;
import org.example.repository.UserAvatarImageRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserRoleRepository;
import org.example.storage.FileSystemStorageService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;



@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserAvatarImageRepository avatarImageRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FileSystemStorageService fileSystemStorageService;

    private final CaptchaSettings captchaSettings;
    private final RestOperations restTemplate;
    protected static final String RECAPTCHA_URL_TEMPLATE = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    public AuthResponseDto register(RegisterDto request,String role_) {
        var user = UserEntity.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .phone("093 839 43 23")
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        var role = roleRepository.findByName(role_);
        var ur = new UserRoleEntity().builder()
                .user(user)
                .role(role)
                .build();
        userRoleRepository.save(ur);

        var jwtToken = jwtService.generateAccessToken(user);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponseDto login(LoginDto request) {
        String url = String.format(RECAPTCHA_URL_TEMPLATE, captchaSettings.getSecret(), request.getReCaptchaToken());
        final GoogleResponse googleResponse = restTemplate.getForObject(url, GoogleResponse.class);
        if (!googleResponse.isSuccess()) {   //перевіряємо чи запит успішний
            //throw new Exception("reCaptcha was not successfully validated");
            return null;
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateAccessToken(user);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponseDto login_google(RegisterDto regDto){

        var user = repository.findByEmail(regDto.getEmail());
        var user_ = user;

        if(user.isPresent())
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            regDto.getEmail(),
                            regDto.getPassword()
                    )
            );
            var old_user = repository.findByEmail(regDto.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateAccessToken(old_user);
            return AuthResponseDto.builder()
                    .token(jwtToken)
                    .build();
        }
        else{
            return register(regDto,Roles.User);
        }

    }

    public String addAvatar(AvatarDTO avaDto){

        var user = repository.findById(avaDto.getUser_id()).get();

        // удаляем старый аватар, если он существует
        var oldAvatar = user.getUserAvatar();
        if (oldAvatar != null) {
            avatarImageRepository.delete(oldAvatar);
            user.setUserAvatar(null);
            repository.save(user);
        }

            var filename = fileSystemStorageService.saveWithMultiePartFile(avaDto.getImage());
            var new_avatar = new UserAvatarEntity().builder()
                    .name(filename)
                    .user(user)
                    .isDeleted(false)
                    .created(new Date())
                    .build();

            avatarImageRepository.save(new_avatar);

            return filename;
    }

    public String getAvatar(FindByIdDTO userId) throws IOException {

        var user = repository.findById(userId.getId()).get();
        var user_ = user;

        var filename = avatarImageRepository.findById(user.getUserAvatar().getId()).get().getName();
        var img = fileSystemStorageService.loadAsResource(filename);

        var resource = fileSystemStorageService.loadAsResource("1200_" + filename);

        var base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
                Paths.get(resource.getFile().toString())));

        return base64;
    }

}