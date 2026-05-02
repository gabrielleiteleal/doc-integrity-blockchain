package br.edu.ifsertaope.doc_integrity_blockchain.service;

import br.edu.ifsertaope.doc_integrity_blockchain.dto.institute.EditInstituteRequestDTO;
import br.edu.ifsertaope.doc_integrity_blockchain.dto.institute.InstituteRequestDTO;
import br.edu.ifsertaope.doc_integrity_blockchain.dto.institute.InstituteResponseDTO;
import br.edu.ifsertaope.doc_integrity_blockchain.exception.institute.InstituteNotFound;
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
                    String wallet = maskWalletAddress(i.getWalletAddress());
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
        String wallet = maskWalletAddress(institute.getWalletAddress());

        Institute saved = repository.save(institute);
        return new InstituteResponseDTO(saved.getId(), saved.getName(), saved.getEmail(), wallet, true);
    }

    @Transactional
    public InstituteResponseDTO editInstitute(Integer instituteId, EditInstituteRequestDTO editInstituteRequestDTO) {
        Institute institute = repository.findById(instituteId).orElseThrow(() -> new InstituteNotFound("Institute not found with id: " + instituteId));

        institute.setName(editInstituteRequestDTO.institute_name());
        institute.setName(editInstituteRequestDTO.institute_email());
        institute.setPasswordHash(passwordEncoder.encode(editInstituteRequestDTO.institute_password_hash()));
        institute.setWalletAddress(editInstituteRequestDTO.institute_wallet_address());
        String wallet = maskWalletAddress(institute.getWalletAddress());

        Institute updated = repository.save(institute);
        return new InstituteResponseDTO(updated.getId(), updated.getName(), updated.getEmail(), wallet, true);
    }

    @Transactional
    public void deleteInstitute(Integer instituteId) {
        repository.deleteById(instituteId);
    }

    private String maskWalletAddress(String walletAddress) {
        if (walletAddress == null || walletAddress.length() < 12) {
            return "0x...";
        }
        return walletAddress.substring(0, 7) + "..." + walletAddress.substring(walletAddress.length() - 5);
    }

}
