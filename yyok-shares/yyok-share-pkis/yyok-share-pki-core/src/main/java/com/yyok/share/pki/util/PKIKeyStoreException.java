package com.yyok.share.pki.util;

/**
 * The exception class thrown by the key storage
 */
public class PKIKeyStoreException extends Exception {

    /**
	 * serial version ID for serialization
	 */
	private static final long serialVersionUID = 1L;

	public PKIKeyStoreException(String message) {
        super(message);
    }
}
