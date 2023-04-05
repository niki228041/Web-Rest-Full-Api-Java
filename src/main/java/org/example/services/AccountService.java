package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.configuration.captcha.CaptchaSettings;
import org.example.configuration.captcha.GoogleResponse;
import org.example.configuration.security.JwtService;
import org.example.constant.Roles;
import org.example.entities.Dto.Auth.AuthResponseDto;
import org.example.entities.Dto.Auth.LoginDto;
import org.example.entities.Dto.Auth.RegisterDto;
import org.example.entities.Entities_Realy.Auth.UserEntity;
import org.example.entities.Entities_Realy.Auth.UserRoleEntity;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserRoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final CaptchaSettings captchaSettings;
    private final RestOperations restTemplate;
    protected static final String RECAPTCHA_URL_TEMPLATE = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    public AuthResponseDto register(RegisterDto request) {
        var user = UserEntity.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .phone("093 839 43 23")
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        var role = roleRepository.findByName(Roles.User);
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

}