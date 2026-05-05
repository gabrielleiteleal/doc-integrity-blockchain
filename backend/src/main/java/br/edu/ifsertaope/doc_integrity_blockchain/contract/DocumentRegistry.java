package br.edu.ifsertaope.doc_integrity_blockchain.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DocumentRegistry extends Contract {

    public static final String BINARY = "";

    protected DocumentRegistry(String contractAddress, Web3j web3j,
                               Credentials credentials, ContractGasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, credentials, gasProvider);
    }

    protected DocumentRegistry(String contractAddress, Web3j web3j,
                               TransactionManager txManager, ContractGasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, txManager, gasProvider);
    }

    public static DocumentRegistry load(String contractAddress, Web3j web3j,
                                        Credentials credentials, ContractGasProvider gasProvider) {
        return new DocumentRegistry(contractAddress, web3j, credentials, gasProvider);
    }

    public static DocumentRegistry load(String contractAddress, Web3j web3j,
                                        TransactionManager txManager, ContractGasProvider gasProvider) {
        return new DocumentRegistry(contractAddress, web3j, txManager, gasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> registerDocument(byte[] documentHashBytes32) {
        final Function function = new Function(
                "registerDocument",
                Collections.singletonList(new Bytes32(documentHashBytes32)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<VerifyResult> verifyDocument(byte[] documentHashBytes32) {
        final Function function = new Function(
                "verifyDocument",
                Collections.singletonList(new Bytes32(documentHashBytes32)),
                Arrays.asList(
                        new TypeReference<Bool>() {
                        },  // isRegistered
                        new TypeReference<Uint256>() {
                        },  // timestamp
                        new TypeReference<Address>() {
                        }   // issuer
                )
        );

        return new RemoteFunctionCall<>(function, () -> {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new VerifyResult(
                    (Boolean) results.get(0).getValue(),
                    (BigInteger) results.get(1).getValue(),
                    (String) results.get(2).getValue()
            );
        });
    }

    public record VerifyResult(
            Boolean isRegistered,
            BigInteger timestamp,
            String issuer
    ) {
    }
}