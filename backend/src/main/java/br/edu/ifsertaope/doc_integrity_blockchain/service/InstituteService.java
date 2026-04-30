package br.edu.ifsertaope.doc_integrity_blockchain.service;

import br.edu.ifsertaope.doc_integrity_blockchain.dto.institute.InstituteRequestDTO;
import br.edu.ifsertaope.doc_integrity_blockchain.dto.institute.InstituteResponseDTO;
import br.edu.ifsertaope.doc_integrity_blockchain.model.Institute;
import br.edu.ifsertaope.doc_integrity_blockchain.repository.IInstitute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstituteService {

    @Autowired
    private IInstitute repository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<InstituteResponseDTO> getAllInstitutes() {
        return repository.findAll().stream()
                .map(i -> {
                    String w = i.getWalletAddress();
                    String wallet = (w != null && w.length() > 12) ? w.substring(0, 7) + "..." + w.substring(w.length() - 5) : "0x...";
                    return new InstituteResponseDTO(i.getId(), i.getName(), i.getEmail(), wallet, true);
                })
                .toList();
    }

    @Transactional
    public InstituteResponseDTO createInstitute(InstituteRequestDTO instituteRequestDTO) {
        if (repository.existsByIdentifier(instituteRequestDTO.institute_identifier())) {
            throw new IllegalArgumentException("Identifier already registered");
        }
        if (repository.existsByEmail(instituteRequestDTO.institute_email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        Institute institute = new Institute();
        institute.setName(instituteRequestDTO.institute_name());
        institute.setIdentifier(instituteRequestDTO.institute_identifier());
        institute.setEmail(instituteRequestDTO.institute_email());
        institute.setPasswordHash(passwordEncoder.encode(instituteRequestDTO.institute_password_hash()));
        institute.setWalletAddress(instituteRequestDTO.institute_wallet_address());

        Institute saved = repository.save(institute);
        return new InstituteResponseDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getWalletAddress(), true);
    }


}
