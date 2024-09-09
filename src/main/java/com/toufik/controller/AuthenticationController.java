package com.toufik.controller;

import com.toufik.dto.AuthenticationResponse;
import com.toufik.dto.LoginRequest;
import com.toufik.dto.RefreshTokenRequest;
import com.toufik.dto.RegisterRequest;
import com.toufik.service.AuthService;
import com.toufik.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthenticationController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        log.info("User Registration for {} Successful", registerRequest.getUsername());
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        try {
            authService.verifyAccount(token);
            log.info("Account Activated Successfully with token {}", token);
        } catch (Exception e) {
            log.error("Account Activation Failed for {}", token);
            return new ResponseEntity<>("Account Activation Failed", OK);
        }
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request received {}", loginRequest.getUsername());
       try {
            return authService.login(loginRequest);
        } catch (Exception e) {
            log.error("Login Failed for {}", loginRequest.getUsername());
            return null;
       }
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        try {
            return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
        }catch (Exception e) {
            return ResponseEntity.status(OK).body("Refresh Token Deletion Failed!!");
        }
    }
}
