package br.edu.ifsertaope.doc_integrity_blockchain.config;


import br.edu.ifsertaope.doc_integrity_blockchain.contract.DocumentRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Slf4j
@Configuration
public class Web3jConfig {

    @Value("${blockchain.rcp-url}")
    private String rcpUrl;

    @Value("${blockchain.private-key}")
    private String privateKey;

    @Value("${blockchain.contract-address}")
    private String contractAddress;

    @Bean
    public Web3j web3j() {
        System.out.println("Configuração do Web3j iniciada. RCP URL: " + rcpUrl);
        return Web3j.build(new HttpService(rcpUrl));
    }

    @Bean
    public Credentials credentials() {
        Credentials creds = Credentials.create(privateKey);
        System.out.println("Credentials criadas com sucesso. Endereço: " + creds.getAddress());
        return creds;
    }

    @Bean
    public DocumentRegistry documentRegistry(Web3j web3j, Credentials credentials) {
        System.out.println("Carregando contrato DocumentRegistry. Endereço do contrato: " + contractAddress);
        return DocumentRegistry.load(contractAddress, web3j, credentials, new DefaultGasProvider());
    }
}
