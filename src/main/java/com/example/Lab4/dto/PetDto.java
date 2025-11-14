package com.example.Lab4.dto;

import com.example.Lab4.repository.PetColor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public final class PetDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private PetColor color;
    private Long ownerId;
    private Set<Long> friends;
}