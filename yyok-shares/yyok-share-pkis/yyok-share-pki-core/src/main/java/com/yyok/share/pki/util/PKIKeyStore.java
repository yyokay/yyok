package com.yyok.share.pki.util;

import org.bouncycastle.asn1.cmp.CMPCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class holds the key and certificate material for authentication
 * and protection of the data
 * To change this template use File | Settings | File Templates.
 */
public class PKIKeyStore {

    private final Logger logger1 = LoggerFactory.getLogger(PKIKeyStore.class);

    private String provider;

    private SecureRandom secureRandom;

    private PrivateKey senderPrivateKey;

    private X509Certificate senderCertificate;

    private PrivateKey caPrivateKey;

    private X509Certificate caCertificate;

    private X509Certificate recipientCertificate;

    private List<X509Certificate> certificateChain;

    private List<CMPCertificate> cmpCertificateChain;

    private X509CRL x509CRL;

    private void init(String provider, String securePRNG) throws NoSuchAlgorithmException {
        x509CRL = null;
        byte[] seed = SecureRandom.getInstance(securePRNG).generateSeed(64);
        this.provider = provider;
        this.certificateChain = new ArrayList<X509Certificate>();
        this.cmpCertificateChain = new ArrayList<CMPCertificate>();
        this.secureRandom = SecureRandom.getInstance(securePRNG);
        this.secureRandom.setSeed(seed);
        //logger.info("PKIKeystore initializes with provider [" + provider + "] and PRNG [" + securePRNG + "]");
    }

    /**
     * Default Constructor
     */
    public PKIKeyStore() throws NoSuchProviderException, NoSuchAlgorithmException {
        init("BC", "SHA1PRNG");
    }

    /**
     * Construct the PKIKeyStore
     * @param senderPrivateKey private key of the sender (RA)
     * @param senderCertificate certificate of the sender (RA)
     * @param recipientCertificate certificate of the receipient (CA or its communication key)
     * @param certificateChain the certificate chain to validate the RA certificate
     */
    public PKIKeyStore(Key senderPrivateKey,
                       Certificate senderCertificate,
                       Key caPrivateKey,
                       Certificate caCertificate,
                       Certificate recipientCertificate,
                       Certificate[] certificateChain) throws NoSuchProviderException, NoSuchAlgorithmException, CertificateEncodingException {
        init("BC", "SHA1PRNG");
        this.senderPrivateKey = (PrivateKey)senderPrivateKey;
        this.senderCertificate = (X509Certificate)senderCertificate;
        this.caCertificate = (X509Certificate)caCertificate;
        this.caPrivateKey = (PrivateKey)caPrivateKey;
        this.recipientCertificate = (X509Certificate)recipientCertificate;
        for (Certificate certificate : certificateChain) {
            this.certificateChain.add((X509Certificate)certificate);
            this.cmpCertificateChain.add(CMPCertificate.getInstance(certificate.getEncoded()));
        }

        //logger.info("PKI Keystore initialized with Sender [" + ((X509Certificate) senderCertificate).getSubjectDN().getName() +"]");
        //logger.info("PKI Keystore initialized with Singer [" + ((X509Certificate) caCertificate).getSubjectDN().getName() +"]");
    }

    /**
     * Construct the PKIKeyStore
     * @param senderPrivateKey private key of the sender (RA)
     * @param senderCertificate certificate of the sender (RA)
     * @param recipientCertificate certificate of the receipient (CA or its communication key)
     * @param certificateChain the certificate chain to validate the RA certificate
     * @param provider the provider name for the cryptographic information
     */
    public PKIKeyStore(Key senderPrivateKey,
                       Certificate senderCertificate,
                       Key caPrivateKey,
                       Certificate caCertificate,
                       Certificate recipientCertificate,
                       Certificate[] certificateChain,
                       String provider,
                       String securePRNG) throws NoSuchProviderException, NoSuchAlgorithmException {
        init(provider, securePRNG);
        this.provider = provider;
        this.senderPrivateKey = (PrivateKey)senderPrivateKey;
        this.senderCertificate = (X509Certificate)senderCertificate;
        this.caCertificate = (X509Certificate)caCertificate;
        this.caPrivateKey = (PrivateKey)caPrivateKey;
        this.recipientCertificate = (X509Certificate)recipientCertificate;
        for (Certificate certificate : certificateChain) {
            this.certificateChain.add((X509Certificate)certificate);
        }
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public SecureRandom getSecureRandom() {
        return secureRandom;
    }

    public void setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    public X509Certificate getSenderCertificate() {
        return senderCertificate;
    }

    public X509Certificate getRecipientCertificate() {
        return recipientCertificate;
    }

    public List<X509Certificate> getCertificateChain() {
        return certificateChain;
    }

    public PrivateKey getCAPrivateKey() {
        return caPrivateKey;
    }

    public X509Certificate getCACertificate() {
        return caCertificate;
    }

    public CMPCertificate[] getCMPCertificateChain() {
        return this.cmpCertificateChain.toArray(new CMPCertificate[this.cmpCertificateChain.size()]);
    }

    public PrivateKey getSenderPrivateKey() {
        return this.senderPrivateKey;
    }

    public void setSenderPrivateKey(PrivateKey senderPrivateKey) {
        this.senderPrivateKey = senderPrivateKey;
    }

    public X509CRL getX509CRL() {
        return x509CRL;
    }

    public void setX509CRL(X509CRL x509CRL) {
        this.x509CRL = x509CRL;
    }

    /**
     * Verifies a certificate against this CA.
     *
     * @param certificate
     * @param signatureDate
     * @throws PKIKeyStoreException
     */
    public void verifyCertificate(X509Certificate certificate, Date signatureDate) throws PKIKeyStoreException {

        try {
            certificate.checkValidity(signatureDate);

            if (x509CRL != null) {
                X509CRLEntry x509CRLEntry = x509CRL.getRevokedCertificate(certificate.getSerialNumber());
                if ((x509CRLEntry != null) &&
                    (x509CRLEntry.getRevocationDate().before(signatureDate))) {
                    throw new PKIKeyStoreException("E: Certificate [" + certificate.getIssuerDN().getName() + ":"
                        + certificate.getSerialNumber().toString() + "] was revoked on "
                        + x509CRLEntry.getRevocationDate().toString() + " because "
                        + x509CRLEntry.getRevocationReason().toString());
                }

            }
            else {
                throw new PKIKeyStoreException("E: CRL is not configured");
            }
        } catch (CertificateExpiredException e) {
            throw new PKIKeyStoreException("E: Certificate expired of [" + certificate.getIssuerDN().getName() + ":"
                + certificate.getSerialNumber().toString() + ":"
                + certificate.getNotAfter().toString() + "]");
        } catch (CertificateNotYetValidException e) {
            throw new PKIKeyStoreException("E: Certificate not yet valid of [" + certificate.getIssuerDN().getName() + ":"
                + certificate.getSerialNumber().toString() + ":"
                + certificate.getNotAfter().toString() + "]");
        }
    }
    /**
     * Generate key-pair
     * 
     * @param keyLength
     * @param AlgorithmId
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public KeyPair generateKeyPair(int keyLength, String AlgorithmId) throws NoSuchAlgorithmException, NoSuchProviderException {
    	KeyPairGenerator kGen = KeyPairGenerator.getInstance(AlgorithmId, provider);
        kGen.initialize(keyLength);
        KeyPair kp = kGen.generateKeyPair();
        //logger.debug("<<< Generating keys\n" + kp.getPrivate().toString() + "\n>>> Generating keys");
        return kp;
    }
}
