package com.yyok.share.kpi.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.yyok.share.pki.jsonview.PKISummary;

import java.util.ArrayList;
import java.util.List;

public class PKI{

	private static final long serialVersionUID = -140537791349423216L;


	private List<CertificateAuthority> cas; 
	
	@JsonView(PKISummary.class)
	private String name;
	
	protected PKI() {}
	
	public PKI(String name, List<CertificateAuthority> cas) {
		this.name = name;
		this.cas = cas;
	}
	
	public PKI(String name, CertificateAuthority ca) {
		this.name = name;
		
		this.cas = new ArrayList<CertificateAuthority>();
		this.cas.add(ca);
	}

	public List<CertificateAuthority> getCas() {
		return cas;
	}

	public String getName() {
		return name;
	}
	
	public void setCas(List<CertificateAuthority> cas) {
		this.cas = cas;
	}

	public void setName(String name) {
		this.name = name;
	}
}
