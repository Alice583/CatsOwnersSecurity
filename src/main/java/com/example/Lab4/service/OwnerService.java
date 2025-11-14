package com.example.Lab4.service;

import com.example.Lab4.dto.OwnerDto;
import com.example.Lab4.repository.Owner;
import com.example.Lab4.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, ModelMapper modelMapper) {
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Page<OwnerDto> findAll(Pageable pageable) {
        return ownerRepository.findAll(pageable).map(owner -> modelMapper.map(owner, OwnerDto.class));
    }

    @Transactional
    public OwnerDto findById(Long id) {
        return ownerRepository.findById(id).map(owner -> modelMapper.map(owner, OwnerDto.class)).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Transactional
    public OwnerDto create(OwnerDto ownerDto) {
        Owner owner = modelMapper.map(ownerDto, Owner.class);
        Owner savedOwner = ownerRepository.save(owner);
        return modelMapper.map(savedOwner, OwnerDto.class);
    }

    @Transactional
    public void delete(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new RuntimeException("Not found");
        }
        ownerRepository.deleteById(id);
    }

    @Transactional
    public OwnerDto update(Long id, String name) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        existingOwner.setName(name);
        Owner updatedOwner = ownerRepository.save(existingOwner);

        return modelMapper.map(updatedOwner, OwnerDto.class);
    }
}
