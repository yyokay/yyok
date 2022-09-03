package com.yyok.share.kpi.domain;

import org.bouncycastle.cert.X509CertificateHolder;


import java.security.PrivateKey;

public class CAIdentityContainer extends IdentityContainer {

	private static final long serialVersionUID = 3657183436298977558L;

	private CertificateAuthority certificateAuthority;

	protected CAIdentityContainer() {};
	
	public CAIdentityContainer (X509CertificateHolder certificate, PrivateKey privateKey) {
		super(certificate, privateKey);
	}
	
	public CertificateAuthority getCertificateAuthority() {
		return certificateAuthority;
	}

	public void setCertificateAuthority(CertificateAuthority certificateAuthority) {
		this.certificateAuthority = certificateAuthority;
	}
}
