package br.edu.ifsertaope.doc_integrity_blockchain.controller;

import br.edu.ifsertaope.doc_integrity_blockchain.dto.document.DocumentResponseDTO;
import br.edu.ifsertaope.doc_integrity_blockchain.model.Document;
import br.edu.ifsertaope.doc_integrity_blockchain.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentResponseDTO> registerDocument(
            @RequestPart("file") MultipartFile file,
            @RequestParam("instituteId") Integer instituteId,
            @RequestParam("documentName") String documentName) {

        Document document = documentService.registerDocument(file, instituteId, documentName);
        DocumentResponseDTO response = new DocumentResponseDTO(
                document.getId(),
                document.getOriginalName(),
                document.getHashSha256(),
                document.getStatusVerification().name(),
                document.getUploadDate());

        return ResponseEntity.status(201).body(response);

    }
}
