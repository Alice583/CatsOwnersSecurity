package com.example.Lab4.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
public class OwnerDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private Set<PetDto> pets;
}