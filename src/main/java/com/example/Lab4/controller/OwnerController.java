package com.example.Lab4.controller;

import com.example.Lab4.dto.OwnerDto;
import com.example.Lab4.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    private final OwnerService ownerService;
    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping()
    public ResponseEntity<Page<OwnerDto>> findAllOwners(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(ownerService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> findByIdOwner(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<OwnerDto> createOwner(@Valid @RequestBody OwnerDto ownerDTO) {
        OwnerDto createdOwner = ownerService.create(ownerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOwner);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<OwnerDto> updatePet(@PathVariable Long id, @Valid @RequestParam String name) {
        OwnerDto updatedOwner = ownerService.update(id, name);
        return ResponseEntity.ok(updatedOwner);
    }
}
