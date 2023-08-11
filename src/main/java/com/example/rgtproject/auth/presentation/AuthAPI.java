package com.example.rgtproject.auth.presentation;

import com.example.rgtproject.auth.application.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthAPI {

    private final AuthService authService;

    @GetMapping("/{registrationId}")
    public ResponseEntity<Void> auth(@RequestParam String code, @PathVariable String registrationId) {
        authService.socialLogin(code, registrationId);
        return ResponseEntity.ok().build();
    }
}
