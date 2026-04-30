package br.edu.ifsertaope.doc_integrity_blockchain.dto.institute;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InstituteResponseDTO(

        @JsonProperty("institute_id")
        Integer id,

        @JsonProperty("institute_name")
        String name,

        @JsonProperty("institute_email")
        String email,

        @JsonProperty("institute_wallet_address")
        String walletAddress,

        @JsonProperty("created")
        boolean created

) {
}
