package br.edu.ifsertaope.doc_integrity_blockchain.exception.document;

public class DocumentAlreadyRegistredException extends RuntimeException {
    public DocumentAlreadyRegistredException(String message) {
        super(message);
    }
}
