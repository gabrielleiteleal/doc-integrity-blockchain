package br.edu.ifsertaope.doc_integrity_blockchain.dto.document;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DocumentResponseDTO(Integer id,
                                  String originalName,
                                  String hashSha256,
                                  String status,
                                  LocalDateTime uploadDate) {
}
