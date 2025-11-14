package com.example.Lab4.service;

import com.example.Lab4.dto.UserDto;
import com.example.Lab4.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, OwnerRepository ownerRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserDto createUser(UserDto userDto) throws IllegalArgumentException {
        Role role;
        try {
            role = Role.valueOf(userDto.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + userDto.getRole());
        }

        Owner owner = ownerRepository.findById(userDto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with id: " + userDto.getOwnerId()));

        User user = modelMapper.map(userDto, User.class);
        user.setRole(role);
        user.setOwner(owner);
        user.setPassword(encoder().encode(userDto.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Transactional
    public UserDto updateUser(Long id, String username) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        existingUser.setName(username);
        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> modelMapper.map(user, UserDto.class));
    }
}
