package com.yyok.share.pki.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;

/**
 * Singleton around PKIKeyStore
 */
public class PKIKeyStoreSingleton {

    static private PKIKeyStore pkiKeyStore = null;

    private PKIKeyStoreSingleton() {
    }

    static public void init(Key senderPrivateKey,
                            Certificate senderCertificate,
                            Key caPrivateKey,
                            Certificate caCertificate,
                            Certificate recipientCertificate,
                            Certificate[] certificateChain,
                            String provider,
                            String securePRNG) throws NoSuchProviderException, NoSuchAlgorithmException {

        pkiKeyStore = new PKIKeyStore(senderPrivateKey,
                senderCertificate,
                caPrivateKey,
                caCertificate,
                recipientCertificate,
                certificateChain,
                provider,
                securePRNG);
    }
    static public PKIKeyStore getInstance() {
        return pkiKeyStore;
    }
}
