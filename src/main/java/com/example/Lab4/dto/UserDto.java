package com.example.Lab4.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String password;
    private String role;
    private Long ownerId;
}