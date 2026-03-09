package com.example.collab.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.collab.domain.valueobject.document.CPF;
import com.example.collab.dto.request.CollaboratorRequestDTO;
import com.example.collab.dto.response.CollaboratorResponseDTO;
import com.example.collab.service.CollaboratorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/collaborators")
public class CollaboratorController {

    
    private CollaboratorService collaboratorService;

    public CollaboratorController(CollaboratorService collaboratorService){

        this.collaboratorService = collaboratorService;
        
    }

    @PostMapping
    public ResponseEntity<CollaboratorResponseDTO> createCollaborator(@RequestBody @Valid CollaboratorRequestDTO body) {

        CollaboratorResponseDTO response = collaboratorService.createCollaborator(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<CollaboratorResponseDTO>> getAllCollaborators() {

        List<CollaboratorResponseDTO> response = collaboratorService.getAllCollaborators();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/registration/{registration}")
    public ResponseEntity<CollaboratorResponseDTO> getCollaboratorByRegistration(@PathVariable Integer registration) {

        CollaboratorResponseDTO response = collaboratorService.getCollaboratorByRegistration(registration);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CollaboratorResponseDTO> getCollaboratorByCPF(@PathVariable String cpf) {

        CollaboratorResponseDTO response = collaboratorService.getCollaboratorByCPF(cpf);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CollaboratorResponseDTO>> getCollaboratorsByName(@PathVariable String name) {

        List<CollaboratorResponseDTO> response = collaboratorService.getCollaboratorByName(name);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/position/{position}")
    public ResponseEntity<List<CollaboratorResponseDTO>> getCollaboratorsByPosition(@PathVariable String position) {

        List<CollaboratorResponseDTO> response = collaboratorService.getCollaboratorByPosition(position);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/bank/{bank}")
    public ResponseEntity<List<CollaboratorResponseDTO>> getCollaboratorsByBank(@PathVariable String bank) {

        List<CollaboratorResponseDTO> response = collaboratorService.getCollaboratorByBank(bank);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{registration}")
    public ResponseEntity<CollaboratorResponseDTO> updateCollaborator(@PathVariable Integer registration,
            @RequestBody @Valid CollaboratorRequestDTO body) {

        CollaboratorResponseDTO response = collaboratorService.updateCollaborator(registration, body);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/{registration}")
    public ResponseEntity<String> deleteCollaborator(@PathVariable Integer registration) {

        String response = collaboratorService.deleteCollaboratorByRegistration(registration);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);

    }

    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<CPF> deleteCollaboratorByCPF(@PathVariable String cpf) {

        CPF response = collaboratorService.deleteCollaboratorByCPF(new CPF(cpf));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);

    }

}
