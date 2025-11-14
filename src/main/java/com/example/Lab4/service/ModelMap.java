package com.example.Lab4.service;

import com.example.Lab4.dto.OwnerDto;
import com.example.Lab4.repository.Owner;
import com.example.Lab4.repository.Pet;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMap {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

//@Configuration
//public class ModelMap {
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.typeMap(Owner.class, OwnerDto.class).addMappings(mapper -> {
//            mapper.map(src -> {
//                List<Pet> pets = src.getPets();
//                return pets != null ? pets.stream().map(Pet::getId).collect(Collectors.toSet()) : Collections.emptySet();
//            }, OwnerDto::setPets);
//        });
//        return modelMapper;
//    }
//}