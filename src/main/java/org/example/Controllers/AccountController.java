package org.example.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.constant.Roles;
import org.example.entities.Dto.Auth.AuthResponseDto;
import org.example.entities.Dto.Auth.GoogleAuthDto;
import org.example.entities.Dto.Auth.LoginDto;
import org.example.entities.Dto.Auth.RegisterDto;
import org.example.google.GoogleAuthService;
import org.example.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;
    private final GoogleAuthService googleAuthService;

    @PostMapping("/google-auth")
    public ResponseEntity<AuthResponseDto> googleLogin(@RequestBody GoogleAuthDto googleAuth) {
        try {
            var googleUserInfo = googleAuthService.verify(googleAuth.getToken());
            RegisterDto request = new RegisterDto().builder()
                    .email(googleUserInfo.getEmail())
                    .firstname("Іван")
                    .lastname("Test Google")
                    .password("0000000")
                    .build();

            var answer = service.login_google(request);

            return ResponseEntity.ok(answer);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody RegisterDto request
    ) {
        return ResponseEntity.ok(service.register(request,Roles.User));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(
            @RequestBody LoginDto request
    ) {
        var auth = service.login(request);
        if(auth==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(auth);
    }
}