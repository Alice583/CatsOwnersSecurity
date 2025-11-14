package com.example.Lab4.service;

import com.example.Lab4.dto.PetDto;
import com.example.Lab4.repository.Pet;
import com.example.Lab4.repository.PetRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PetService(PetRepository petRepository, ModelMapper modelMapper) {
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public PetDto create(PetDto petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Pet savedPet = petRepository.save(pet);
        return modelMapper.map(savedPet, PetDto.class);
    }

    @Transactional
    public PetDto findById(Long id) {
        return petRepository.findById(id).map(pet -> modelMapper.map(pet, PetDto.class)).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Transactional
    public Page<PetDto> findAll(Pageable pageable) {
        return petRepository.findAll(pageable).map(pet -> modelMapper.map(pet, PetDto.class));
    }

    @Transactional
    public void delete(Long id) {
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Not found");
        }
        petRepository.deleteById(id);
    }

    @Transactional
    public PetDto updatePet(Long id, PetDto petDTO) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        modelMapper.map(petDTO, existingPet);
        Pet updatedPet = petRepository.save(existingPet);
        return modelMapper.map(updatedPet, PetDto.class);
    }

    @Transactional
    public void makeFriend(Long petId, Long friendId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        Pet friend = petRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        pet.getFriends().add(friend);
        friend.getFriends().add(pet);
        petRepository.save(pet);
        petRepository.save(friend);
    }

    @Transactional
    public void removeFriend(Long petId, Long friendId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        Pet friend = petRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        pet.getFriends().remove(friend);
        friend.getFriends().remove(pet);
        petRepository.save(pet);
        petRepository.save(friend);
    }
}
