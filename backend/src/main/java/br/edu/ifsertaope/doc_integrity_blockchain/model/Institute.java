package br.edu.ifsertaope.doc_integrity_blockchain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "institute")
@Getter
@Setter
public class Institute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institute_id", nullable = false)
    private Integer id;

    @Column(name = "institute_name", nullable = false)
    private String name;

    @Column(name = "institute_identifier", nullable = false, unique = true)
    private String identifier;

    @Column(name = "institute_email", nullable = false, unique = true)
    private String email;

    @Column(name = "institute_password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "institute_wallet_address", nullable = false)
    private String walletAddress;

    @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
    private Set<Document> documents;
}
