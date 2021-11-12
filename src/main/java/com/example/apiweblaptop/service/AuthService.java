package com.example.apiweblaptop.service;

import com.example.apiweblaptop.payload.request.ChangPasswordRequest;
import com.example.apiweblaptop.payload.request.LoginRequest;
import com.example.apiweblaptop.payload.request.SignupRequest;
import com.example.apiweblaptop.payload.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public JwtResponse getJwtResponse(LoginRequest loginRequest);

    public ResponseEntity<?> getUserSignUp(SignupRequest signUpRequest);

    public ResponseEntity<?> getUserChangePassword(ChangPasswordRequest changPasswordRequest);
}
