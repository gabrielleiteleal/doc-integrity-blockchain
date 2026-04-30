package br.edu.ifsertaope.doc_integrity_blockchain.repository;

import br.edu.ifsertaope.doc_integrity_blockchain.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInstitute extends JpaRepository<Institute, Integer> {
    boolean existsByIdentifier(String identifier);
    boolean existsByEmail(String email);
}
