package br.edu.ifsertaope.doc_integrity_blockchain.dto.institute;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditInstituteRequestDTO(
        @JsonProperty("institute_name")
        @NotBlank(message = "institute_name is required")
        @Size(max = 255, message = "institute_name must have at most 255 characters")
        String institute_name,

        @JsonProperty("institute_email")
        @NotBlank
        @Email(message = "institute_email must be a valid email")
        @Size(max = 255, message = "institute_email must have at most 255 characters")
        String institute_email,

        @JsonProperty("institute_password_hash")
        @NotBlank(message = "institute_password_hash is required")
        @Size(min = 8, max = 72, message = "institute_password_hash must be between 8 and 72 characters")
        String institute_password_hash,

        @JsonProperty("institute_wallet_address")
        @NotBlank(message = "institute_wallet_address is required")
        @Size(max = 255, message = "institute_wallet_address must have at most 255 characters")
        @NotBlank
        String institute_wallet_address
) {
}
