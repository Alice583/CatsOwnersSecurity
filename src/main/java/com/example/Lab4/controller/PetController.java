package com.example.Lab4.controller;

import com.example.Lab4.dto.PetDto;
import com.example.Lab4.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/create")
    public ResponseEntity<PetDto> createPet(@RequestBody PetDto petDTO) {
        return ResponseEntity.ok(petService.create(petDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> FindByIdPet(@PathVariable Long id) {
        return ResponseEntity.ok(petService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<Page<PetDto>> findAllPets(Pageable pageable) {
        return ResponseEntity.ok(petService.findAll(pageable));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetDto> updatePet(
            @PathVariable Long id,
            @RequestBody PetDto petDTO) {
        return ResponseEntity.ok(petService.updatePet(id, petDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
