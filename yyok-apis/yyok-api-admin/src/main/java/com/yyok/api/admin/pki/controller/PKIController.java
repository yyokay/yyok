package com.yyok.api.admin.pki.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.yyok.share.kpi.domain.IdentityContainer;
import com.yyok.share.kpi.domain.PKI;
import com.yyok.share.kpi.service.ICRLService;
import com.yyok.share.kpi.service.IPKCS12ConversorService;
import com.yyok.share.kpi.service.IPKIService;
import com.yyok.share.kpi.utils.GeneralUtils;
import com.yyok.share.pki.jsonview.PKISummary;
import io.swagger.annotations.Api;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.util.List;

/**
 * 
 * @author fabio.resner
 *
 */
@RestController
@Api(tags = "系统：KPI管理")
@RequestMapping("/sys/pki")
public class PKIController {

	@Autowired
	private IPKIService pkiService;
	
	@Autowired
	private IPKCS12ConversorService p12Conversor;
	
	@Autowired
	private ICRLService crlService;
	
	private static final String P12_EXTENSTION = ".p12";
	private static final String CRL_EXTENSTION = ".crl";
	private static final String P7B_EXTENSTION = ".p7b";
	private static final String CHAIN_ID = "Chain";
	private static final String CONTENT_DISPOSITION_HEADER = "content-disposition";
	private static final String CONTENT_DISPOSITION_ARGS = "attachment; filename=";
	
	@RequestMapping(path = "/new", method = RequestMethod.POST)
	public void generateNewPKI(@RequestParam String pkiName) {
		pkiService.generatePKI(pkiName);
	}
	
	@JsonView(PKISummary.class)
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<PKI> listPKIs() {
		return pkiService.listPKIs();
	}
	
	//TODO Review exception thrown at this point
	//TODO Response Entity returning could be revised (better standard ways to implement?)
	@RequestMapping(path="/cert/new", method = RequestMethod.POST)
	public ResponseEntity<byte[]> newPKICertificate(@RequestParam String pkiName, @RequestParam String subjectName, @RequestParam String password) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		IdentityContainer userIdentity = this.pkiService.generateIdentity(pkiName, subjectName);
		KeyStore userPKCS12 = p12Conversor.generatePKCS12(userIdentity);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		userPKCS12.store(output, password.toCharArray());
		
		String certificateCN = GeneralUtils.getParsedCertificateCommonName(userIdentity.getCertificate());
		
		return ResponseEntity.ok()
				.header(CONTENT_DISPOSITION_HEADER, CONTENT_DISPOSITION_ARGS + certificateCN + P12_EXTENSTION)
				.body(Base64.encode(output.toByteArray()));
	}
	
	//TODO Review exception thrown at this point
	//TODO Response Entity returning could be revised (better standard ways to implement?)
	//TODO CRLs are being generated each time the service is invoked. (fetch from database?)
	@RequestMapping(path="/{issuerName}/crl", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getCRLs(@PathVariable String issuerName) throws CRLException {
		X509CRL crl = this.crlService.generateCRL(issuerName);
		
		String crlName = GeneralUtils.getParsedCRLX500Principal(crl);
		
		return ResponseEntity.ok()
				.header(CONTENT_DISPOSITION_HEADER, CONTENT_DISPOSITION_ARGS + crlName + CRL_EXTENSTION)
				.body(Base64.encode(crl.getEncoded()));
	}
	
	//TODO Review exception thrown at this point
	//TODO Response Entity returning could be revised (better standard ways to implement?)
	@RequestMapping(path="/{issuerName}/cert", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getAIA(@PathVariable String issuerName) throws CRLException, IOException {
		CMSSignedData chain = this.pkiService.getCertificateChain(issuerName);
		
		return ResponseEntity.ok()
				.header(CONTENT_DISPOSITION_HEADER, CONTENT_DISPOSITION_ARGS + issuerName + CHAIN_ID + P7B_EXTENSTION)
				.body(Base64.encode(chain.getEncoded()));
	}
}
