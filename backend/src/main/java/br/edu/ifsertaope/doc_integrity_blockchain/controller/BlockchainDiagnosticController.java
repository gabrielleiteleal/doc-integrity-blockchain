package br.edu.ifsertaope.doc_integrity_blockchain.controller;

import br.edu.ifsertaope.doc_integrity_blockchain.contract.DocumentRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.utils.Convert;

import java.util.Map;

import static br.edu.ifsertaope.doc_integrity_blockchain.utils.HashUtils.hexToBytes32;

@RestController
@RequestMapping("/api/diagnostic")
@RequiredArgsConstructor
public class BlockchainDiagnosticController {

    @Autowired
    private DocumentRegistry documentRegistry;

    private final Web3j web3j;
    private final Credentials credentials;

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() throws Exception {

        var balance = web3j
                .ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send();

        var maticBalance = Convert.fromWei(
                balance.getBalance().toString(), Convert.Unit.ETHER
        );

        var blockNumber = web3j.ethBlockNumber().send();

        return ResponseEntity.ok(Map.of(
                "wallet", credentials.getAddress(),
                "balance", maticBalance.toPlainString() + "MATIC",
                "blockNumber", blockNumber.getBlockNumber(),
                "network", "Polygon Amoy (chainId: 80002)"
        ));
    }

    @GetMapping("/contract/ping")
    public ResponseEntity<Map<String, Object>> pingContract(){
        try {
            String fakeHex = "a".repeat(64);
            byte[] fakeBytes32 = hexToBytes32(fakeHex);

            DocumentRegistry.VerifyResult result = documentRegistry
                    .verifyDocument(fakeBytes32)
                    .send();

            return ResponseEntity.ok(Map.of(
                    "contract", "acessível",
                    "isRegistered", result.isRegistered(),
                    "issuer", result.issuer(),
                    "timestamp", result.timestamp()
            ));

        } catch (Exception e){
            return ResponseEntity.status(502).body(Map.of(
                    "contract", "inacessível",
                    "error", e.getMessage()
            ));
        }
    }

}
