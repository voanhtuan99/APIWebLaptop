package com.example.apiweblaptop.service.impl;

import com.example.apiweblaptop.entity.Role;
import com.example.apiweblaptop.entity.RoleName;
import com.example.apiweblaptop.entity.User;
import com.example.apiweblaptop.payload.request.ChangPasswordRequest;
import com.example.apiweblaptop.payload.request.LoginRequest;
import com.example.apiweblaptop.payload.request.SignupRequest;
import com.example.apiweblaptop.payload.response.JwtResponse;
import com.example.apiweblaptop.payload.response.MessageResponse;
import com.example.apiweblaptop.repo.RoleRepository;
import com.example.apiweblaptop.repo.UserRepository;
import com.example.apiweblaptop.security.jwt.JwtUtils;
import com.example.apiweblaptop.security.services.UserDetailsImpl;
import com.example.apiweblaptop.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    final private AuthenticationManager authenticationManager;

    final private JwtUtils jwtUtils;

    final private UserRepository userRepository;

    final private RoleRepository roleRepository;

    final private PasswordEncoder encoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public JwtResponse getJwtResponse(LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // if go there, the user/password is correct
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate jwt to return to client
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public ResponseEntity<?> getUserSignUp(SignupRequest signUpRequest)
    {

//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        if (userRepository.existsByPhone(signUpRequest.getPhone_number())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Phone number is already in use!"));
//        }

        // Create new user's account
        User user = new User();
        user.setName(signUpRequest.getUsername().trim());
        user.setEmail(signUpRequest.getEmail().trim());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setAddress(signUpRequest.getAddress().trim());
        user.setPhone_number(signUpRequest.getPhone());
        String strRoles = signUpRequest.getRole();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            user.setRole(userRole);
        } else {
            switch (strRoles.toLowerCase()) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    System.out.println(adminRole);
                    user.setRole(adminRole);

                    break;
                case "pm":
                    Role modRole = roleRepository.findByName(RoleName.STAFF)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    user.setRole(modRole);
                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    user.setRole(userRole);
            }
            user.setActivestatus("Active");
            userRepository.save(user);
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> getUserChangePassword(ChangPasswordRequest changPasswordRequest)
    {
        if (changPasswordRequest.getNewpassword().equals(""))
        {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Enter new password!"));
        }

        if (!changPasswordRequest.getConfirmpassword().equals(changPasswordRequest.getNewpassword()))
        {
            return ResponseEntity.ok()
                    .body(new MessageResponse("Confirm Password is not correct!"));
        }
        User user = userRepository.findById(changPasswordRequest.getUser_id()).get();

        user.setPassword(encoder.encode(changPasswordRequest.getNewpassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Change Password successfully!"));
    }
}
