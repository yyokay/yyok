package com.yyok.share.kpi.domain;

public class CertificateAuthority {

	private static final long serialVersionUID = 2939716867481218950L;

	private String name;

	private CAIdentityContainer identityContainer;

	private PKI pki;

	protected CertificateAuthority() {}

	public CertificateAuthority(String caName, CAIdentityContainer identityContainer) {
		this.identityContainer = identityContainer;
		this.name = caName;
	}

	public String getName() {
		return name;
	}

	public void setName(String caName) {
		this.name = caName;
	}

	public PKI getPki() {
		return pki;
	}

	public void setPki(PKI pki) {
		this.pki = pki;
	}

	public CAIdentityContainer getIdentityContainer() {
		return identityContainer;
	}

	public void setIdentityContainer(CAIdentityContainer identityRepository) {
		this.identityContainer = identityRepository;
	}
}