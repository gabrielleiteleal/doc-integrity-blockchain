package br.edu.ifsertaope.doc_integrity_blockchain.controller;

import br.edu.ifsertaope.doc_integrity_blockchain.dto.institute.EditInstituteRequestDTO;
import br.edu.ifsertaope.doc_integrity_blockchain.dto.institute.InstituteRequestDTO;
import br.edu.ifsertaope.doc_integrity_blockchain.dto.institute.InstituteResponseDTO;
import br.edu.ifsertaope.doc_integrity_blockchain.service.InstituteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/institute")
public class InstituteController {

    @Autowired
    private InstituteService instituteService;

    @GetMapping
    public ResponseEntity<?> getAllInstitutes() {
        return ResponseEntity.status(200).body(instituteService.getAllInstitutes());
    }

    @PostMapping
    public ResponseEntity<InstituteResponseDTO> createInstitute(@Valid @RequestBody InstituteRequestDTO instituteRequestDTO) {
        InstituteResponseDTO response = instituteService.createInstitute(instituteRequestDTO);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{instituteId}")
    public ResponseEntity<InstituteResponseDTO> editInstitute(@PathVariable Integer instituteId, @Valid @RequestBody EditInstituteRequestDTO editInstituteRequestDTO) {
        InstituteResponseDTO response = instituteService.editInstitute(instituteId, editInstituteRequestDTO);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{instituteId}")
    public ResponseEntity<Void> deleteInstitute(@PathVariable Integer instituteId) {
        instituteService.deleteInstitute(instituteId);
        return ResponseEntity.status(204).build();
    }

}
