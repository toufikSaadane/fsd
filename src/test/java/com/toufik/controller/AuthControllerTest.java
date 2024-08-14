package com.toufik.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.toufik.dto.AuthenticationResponse;
import com.toufik.dto.LoginRequest;
import com.toufik.dto.RefreshTokenRequest;
import com.toufik.dto.RegisterRequest;
import com.toufik.service.AuthService;
import com.toufik.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

  @Mock
  private AuthService authService;

  @Mock
  private RefreshTokenService refreshTokenService;

  @InjectMocks
  private AuthenticationController authController;

  @Test
  public void signup_ReturnsSuccessMessage_WhenSignupIsSuccessful() {
    RegisterRequest registerRequest = new RegisterRequest("username", "password", "email@example.com");
    ResponseEntity<String> response = authController.signup(registerRequest);
    assert response.getStatusCode() == HttpStatus.OK;
    assert response.getBody().equals("User Registration Successful");
    verify(authService, times(1)).signup(eq(registerRequest));
  }

  @Test
  public void verifyAccount_ReturnsSuccessMessage_WhenAccountVerificationIsSuccessful() {
    String token = "verificationToken";
    ResponseEntity<String> response = authController.verifyAccount(token);
    assert response.getStatusCode() == HttpStatus.OK;
    assert response.getBody().equals("Account Activated Successfully");
    verify(authService, times(1)).verifyAccount(eq(token));
  }

  @Test
  public void login_ReturnsAuthenticationResponse() {
    LoginRequest loginRequest = new LoginRequest("username", "password");
    AuthenticationResponse authenticationResponse = authController.login(loginRequest);
    verify(authService, times(1)).login(eq(loginRequest));
  }

  @Test
  public void refreshTokens_ReturnsAuthenticationResponse() {
    RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
    AuthenticationResponse authenticationResponse = authController.refreshTokens(refreshTokenRequest);
    verify(authService, times(1)).refreshToken(eq(refreshTokenRequest));
  }

  @Test
  public void logout_ReturnsSuccessMessageAndDeletesRefreshToken() {
    RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
    ResponseEntity<String> response = authController.logout(refreshTokenRequest);
    assert response.getStatusCode() == HttpStatus.OK;
    assert response.getBody().equals("Refresh Token Deleted Successfully!!");
    verify(refreshTokenService, times(1)).deleteRefreshToken(eq(refreshTokenRequest.getRefreshToken()));
  }
}
