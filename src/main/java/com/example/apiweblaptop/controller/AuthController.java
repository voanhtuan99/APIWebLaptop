package com.example.apiweblaptop.controller;

import com.example.apiweblaptop.payload.request.ChangPasswordRequest;
import com.example.apiweblaptop.payload.request.LoginRequest;
import com.example.apiweblaptop.payload.request.SignupRequest;
import com.example.apiweblaptop.payload.response.JwtResponse;
import com.example.apiweblaptop.payload.response.MessageResponse;
import com.example.apiweblaptop.repo.RoleRepository;
import com.example.apiweblaptop.repo.UserRepository;
import com.example.apiweblaptop.security.jwt.JwtAuthTokenFilter;
import com.example.apiweblaptop.security.jwt.JwtUtils;
import com.example.apiweblaptop.service.AuthService;
import com.example.apiweblaptop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    final private AuthenticationManager authenticationManager;

    final private UserRepository userRepository;

    final private RoleRepository roleRepository;

    final private PasswordEncoder encoder;

    final private JwtUtils jwtUtils;

    public AuthController (AuthenticationManager authenticationManager, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // TODO, authenticate when login
        JwtResponse jp = authService.getJwtResponse(loginRequest);

        return ResponseEntity.ok(jp);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.getUserSignUp(signUpRequest);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> changePasswordUser(@Valid @RequestBody ChangPasswordRequest changPasswordRequest)
    {
        return authService.getUserChangePassword(changPasswordRequest);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jwt = JwtAuthTokenFilter.parseJwt(request);
        if (jwt != null && jwtUtils.addToBlackList(jwt)){
            return ResponseEntity.ok(new MessageResponse("User logged out successfully!"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Cannot add jwt token to blacklist!"));
    }
}
