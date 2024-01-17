package com.example.tiwo.DTOs;

import lombok.Data;

import java.util.UUID;

@Data
public class RegistrationDTO {
    private String email;
    private String username;
    private String password;

}