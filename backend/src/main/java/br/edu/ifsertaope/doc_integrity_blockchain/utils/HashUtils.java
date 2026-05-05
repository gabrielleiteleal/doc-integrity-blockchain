package br.edu.ifsertaope.doc_integrity_blockchain.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HashUtils {

    private HashUtils(){}

    public static String generateSHA256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data);
            return HexFormat.of().formatHex(hashBytes); // Java 17+
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 não disponível na JVM.", e);
        }
    }

    public static byte[] hexToBytes32(String hexHash) {
        if (hexHash == null || hexHash.length() != 64) {
            throw new IllegalArgumentException(
                    "Hash inválido: esperado 64 chars hex, recebido: "
                            + (hexHash == null ? "null" : hexHash.length() + " chars"));
        }
        return HexFormat.of().parseHex(hexHash);
    }
}
