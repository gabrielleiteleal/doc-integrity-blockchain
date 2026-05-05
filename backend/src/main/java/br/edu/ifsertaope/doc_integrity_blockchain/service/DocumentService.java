package br.edu.ifsertaope.doc_integrity_blockchain.service;

import br.edu.ifsertaope.doc_integrity_blockchain.exception.document.DocumentAlreadyRegistredException;
import br.edu.ifsertaope.doc_integrity_blockchain.exception.document.DocumentRegistrationException;
import br.edu.ifsertaope.doc_integrity_blockchain.exception.institute.InstituteNotFound;
import br.edu.ifsertaope.doc_integrity_blockchain.model.Document;
import br.edu.ifsertaope.doc_integrity_blockchain.model.Institute;
import br.edu.ifsertaope.doc_integrity_blockchain.repository.IDocument;
import br.edu.ifsertaope.doc_integrity_blockchain.repository.IInstitute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    @Autowired
    IDocument documentRepository;
    @Autowired
    IInstitute instituteRepository;

    @Transactional
    public Document registerDocument(MultipartFile file, Integer instituteId, String documentName) {

        Institute institute = instituteRepository.findById(instituteId).orElseThrow(() -> new InstituteNotFound("Institute not found with id: " + instituteId));
        System.out.println("Iniciando registro do documento " + documentName + " para a instituição " + institute.getName());

        byte[] fileBytes = extractBytes(file);

        String hash = generateSHA256(fileBytes);
        System.out.println("Hash SHA-256 gerado" + hash);

        if (documentRepository.existsByHashSha256(hash)) {
            System.out.println("Documento duplicado" + hash);
            throw new DocumentAlreadyRegistredException("This document has already been registered. Hash: " + hash);
        }

        Document document = new Document();
        document.setOriginalName(documentName);
        document.setHashSha256(hash);
        document.setInstitute(institute);
        document.setUploadDate(LocalDateTime.now());
        document.setStatusVerification(Document.StatusVerification.PENDING);

        document = documentRepository.save(document);
        System.out.println("Documento salvo no banco de dados com id: " + document.getId());

        return document;
    }

    private byte[] extractBytes(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            if (bytes.length == 0) {
                throw new DocumentRegistrationException("The sent file is empty.");
            }
            return bytes;
        } catch (IOException e) {
            throw new DocumentRegistrationException("Failed to extract bytes from file" + e);
        }
    }

    private String generateSHA256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data);
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Failed to generate SHA-256 hash: " + e.getMessage());
        }
    }
}
