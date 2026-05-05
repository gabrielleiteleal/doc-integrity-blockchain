package br.edu.ifsertaope.doc_integrity_blockchain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documents_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", nullable = false)
    private Institute institute;

    @Column(name = "documents_original_name", nullable = false)
    private String originalName;

    @Column(name = "documents_hash_sha256", nullable = false, unique = true)
    private String hashSha256;

    @Column(name = "documents_upload_date", nullable = false)
    private LocalDateTime uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_verification", nullable = false)
    private StatusVerification statusVerification;

    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BlockchainTransaction blockchainTransaction;

    public enum StatusVerification {
        PENDING, REGISTRED, FAILURE
    }
}
