package br.edu.ifsertaope.doc_integrity_blockchain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "blockchain_transactions")
@Data
public class BlockchainTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blockchain_transactions_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "tx_hash", nullable = false)
    private String txHash;

    @Column(name = "confirmation_block", nullable = false)
    private Long confirmationBlock;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_tx", nullable = false)
    private StatusTx statusTx;

    @Column(name = "log_error", nullable = false, columnDefinition = "TEXT")
    private String logError;

    public enum StatusTx {
        MINING, SUCCESS, REVERTED
    }
}