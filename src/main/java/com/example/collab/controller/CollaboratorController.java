package com.example.collab.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.collab.domain.valueobject.document.CPF;
import com.example.collab.dto.request.CollaboratorRequestDTO;
import com.example.collab.dto.response.CollaboratorResponseDTO;
import com.example.collab.service.CollaboratorService;
import com.example.collab.service.PhotoStorageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/collaborators")
public class CollaboratorController {

    
    private CollaboratorService collaboratorService;

    private PhotoStorageService photoStorageService;

    public CollaboratorController(CollaboratorService collaboratorService, PhotoStorageService photoStorageService){

        this.collaboratorService = collaboratorService;

        this.photoStorageService = photoStorageService;
        
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

    @PostMapping("/{registration}/photo")
    public ResponseEntity<CollaboratorResponseDTO> uploadPhoto(@PathVariable Integer registration,
            @RequestParam("photo") MultipartFile file) {

        CollaboratorResponseDTO response = collaboratorService.uploadPhoto(registration, file);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/{registration}/photo")
    public ResponseEntity<Resource> getPhoto(@PathVariable Integer registration) {

        CollaboratorResponseDTO collaborator = collaboratorService.getCollaboratorByRegistration(registration);

        if (collaborator.photo() == null || collaborator.photo().isBlank()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

        Resource resource = photoStorageService.loadPhoto(collaborator.photo());

        String contentType = detectContentType(collaborator.photo());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);

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

    private String detectContentType(String path) {

        if (path.endsWith(".png")) {

            return "image/png";

        } else if (path.endsWith(".gif")) {

            return "image/gif";

        } else if (path.endsWith(".webp")) {

            return "image/webp";

        }

        return "image/jpeg";

    }

}
