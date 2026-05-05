package br.edu.ifsertaope.doc_integrity_blockchain.repository;

import br.edu.ifsertaope.doc_integrity_blockchain.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDocument extends JpaRepository<Document, Integer> {
    boolean existsByHashSha256(String hashSha256);

    Optional<Document> findByHashSha256(String hashSha256);
}
