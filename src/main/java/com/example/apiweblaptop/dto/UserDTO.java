package com.example.apiweblaptop.dto;

import com.example.apiweblaptop.entity.User;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone_number;
    private String address;
    private Long role;
    private String activestatus;


    public UserDTO entityToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setAddress(user.getAddress());
        dto.setPhone_number(user.getPhone_number());
        dto.setActivestatus(user.getActivestatus());
        dto.setRole(user.getRole().getId());
        return dto;
    }

    public User dtoToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone_number(dto.getPhone_number());
        user.setAddress(dto.getAddress());
        user.setActivestatus(dto.getActivestatus());
        return user;
    }

    public List<UserDTO> entityToDTO(List<User> users) {
        return users.stream().map(x -> entityToDTO(x)).collect(Collectors.toList());
    }

    public List<User> dtoToEntity(List<UserDTO> dtos) {
        return dtos.stream().map(x-> dtoToEntity(x)).collect(Collectors.toList());
    }
}
