package org.example.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.entities.Dto.Auth.AuthResponseDto;
import org.example.entities.Dto.Auth.LoginDto;
import org.example.entities.Dto.Auth.RegisterDto;
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

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody RegisterDto request
    ) {
        return ResponseEntity.ok(service.register(request));
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