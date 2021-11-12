package com.example.apiweblaptop.controller;

import com.example.apiweblaptop.dto.UserDTO;
import com.example.apiweblaptop.entity.Role;
import com.example.apiweblaptop.entity.User;


import com.example.apiweblaptop.exception.UserException;
import com.example.apiweblaptop.payload.response.MessageResponse;
import com.example.apiweblaptop.service.RoleService;
import com.example.apiweblaptop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

//    @Autowired
//    private ModelMapper modelMapper;

    final private PasswordEncoder encoder;

    public UserController(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @GetMapping("")

    public List<UserDTO> getAllUsers() {
        List<UserDTO> usersDTO = new ArrayList<>();
        List<User> users = userService.retrieveUsers();
        for (int i = 0; i < users.size(); i++) {
            UserDTO u = new UserDTO().entityToDTO(users.get(i));
            usersDTO.add(u);
        }
        return usersDTO;
    }

//    @GetMapping("/active")
//    public int getAllUsersActive() {
//        List<User> users = userService.getUsersActive();
//        return users.size();
//    }
//
    @GetMapping("/find/{userId}")
    public UserDTO findUser(@PathVariable Long userId) {
        User us = userService.getUser(userId);
        if (us == null) {
            throw new UserException(userId);
        }
        return new UserDTO().entityToDTO(userService.getUser(userId));
    }
//
//    @GetMapping("/page")
//    @ApiOperation(value = "Get Users By Pages")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
//            @ApiResponse(code = 400, message = "Bad request"),
//            @ApiResponse(code = 500, message = "Internal server error")})
//    public ResponseEntity<List<User>> getUsersPages(UserPage userPage) {
//        return new ResponseEntity<>(userService.getUsersPage(userPage), HttpStatus.OK);
//    }
//
//    @GetMapping("/customer")
//    public List<UserDTO> getAllCustomers() {
//        List<UserDTO> usersDTO = new ArrayList<>();
//        List<User> users = userService.getUsers();
//        for (int i = 0; i < users.size(); i++) {
//            UserDTO u = convertToDTO(users.get(i));
//            usersDTO.add(u);
//        }
//        return usersDTO;
//    }
//
//    @GetMapping("/employee")
//    public List<UserDTO> getAllEmployees() {
//        List<UserDTO> usersDTO = new ArrayList<>();
//        List<User> users = userService.getEmployee();
//        for (int i = 0; i < users.size(); i++) {
//            UserDTO u = convertToDTO(users.get(i));
//            usersDTO.add(u);
//        }
//        return usersDTO;
//    }
//
//    @GetMapping("/username/")
//    public int getUsername(@RequestParam String username) {
//        List<User> users = userService.getUserAccount(username);
//        return users.size();
//    }
//
//    @GetMapping("/usernamePage")
//    public ResponseEntity<List<User>> getUsernamePages(@RequestParam String username, UserPage userPage) {
//        return new ResponseEntity<>(userService.getUserAccountPage(userPage, username), HttpStatus.OK);
//    }
//
    @PostMapping("/add")
//    @ApiOperation(value = "Create User")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
//            @ApiResponse(code = 400, message = "Bad request"),
//            @ApiResponse(code = 500, message = "Internal server error")})
    public UserDTO saveUser(@RequestBody UserDTO user) {
        user.setActivestatus("Active");
        System.out.println(user);
        UserDTO userDTO = userService.saveUser(user);
        return userDTO;
    }

    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable(name = "userId") Long userId, @Valid @RequestBody UserDTO userDetails) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new UserException(userId);
        } else {
            UserUpdate(user, userDetails);
            userService.updateUser(user);
        }
        return new UserDTO().entityToDTO(user);
    }
//
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") Long userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new UserException(userId);
        }
        user.setActivestatus("Inactive");
        userService.updateUser(user);
        return ResponseEntity.ok(new MessageResponse("Delete Successfully"));
    }
//
//    private UserDTO convertToDTO(User u) {
//        UserDTO udto = modelMapper.map(u, UserDTO.class);
//        String role_id = String.valueOf(u.getRole().getId());
//        udto.setRole_id(role_id);
//        return udto;
//    }
//
    private void UserUpdate(User user, UserDTO userDetails) {
        user.setName(userDetails.getName().trim());
        user.setAddress(userDetails.getAddress().trim());
        user.setEmail(userDetails.getEmail().trim());
        user.setPhone_number(userDetails.getPhone_number());
        user.setActivestatus(userDetails.getActivestatus());
        Role r = roleService.getRole(Long.valueOf(userDetails.getRole()));
        user.setRole(r);
    }
}