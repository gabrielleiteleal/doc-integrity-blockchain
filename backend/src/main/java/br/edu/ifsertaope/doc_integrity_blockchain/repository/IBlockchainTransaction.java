package br.edu.ifsertaope.doc_integrity_blockchain.repository;

import br.edu.ifsertaope.doc_integrity_blockchain.model.BlockchainTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlockchainTransaction extends JpaRepository<BlockchainTransaction, Integer> {
}
