package com.yyok.share.pki.ca.client;

import org.bouncycastle.asn1.cmp.PKIStatus;
import org.bouncycastle.cert.X509CertificateHolder;

import java.security.KeyPair;

public class ProcessCertificationResult {
	PKIStatus pkiStatus;
	
	X509CertificateHolder x509CertificateHolder;
	
	KeyPair keyPair;

	public ProcessCertificationResult(PKIStatus pkiStatus, X509CertificateHolder x509CertificateHolder, KeyPair keyPair) {
		this.pkiStatus = pkiStatus;
		this.x509CertificateHolder = x509CertificateHolder;
		this.keyPair = keyPair;
	}
	
	public PKIStatus getPkiStatus() {
		return pkiStatus;
	}

	public void setPkiStatus(PKIStatus pkiStatus) {
		this.pkiStatus = pkiStatus;
	}

	public X509CertificateHolder getX509CertificateHolder() {
		return x509CertificateHolder;
	}

	public void setX509CertificateHolder(X509CertificateHolder x509CertificateHolder) {
		this.x509CertificateHolder = x509CertificateHolder;
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}
	
	
}
