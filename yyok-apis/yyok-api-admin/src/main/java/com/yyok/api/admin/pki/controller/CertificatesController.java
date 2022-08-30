package com.yyok.api.admin.pki.controller;

import com.yyok.share.kpi.service.ICertificateKeyPairGeneratorService;
import com.yyok.share.kpi.service.ICertificateService;
import io.swagger.annotations.Api;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 
 * @author fabio.resner
 *
 */
@RestController
@Api(tags = "系统：KPI证书管理")
@RequestMapping("/sys/cert")
public class CertificatesController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CertificatesController.class);

	@Autowired
	private ICertificateService certService;
	
	@Autowired
	private ICertificateKeyPairGeneratorService keyPairGenerator;
	
	//TODO Review exception thrown at this point
	//TODO Response Entity returning could be revised (could be done better)
	@RequestMapping(path="/new/self-signed", method=RequestMethod.POST)
	public ResponseEntity<byte[]> generateSelfSignedCerficate(@RequestParam String subjectName) throws IOException {
		LOGGER.info("Received new self-signed certificate request");
		
		X509CertificateHolder certificate = certService.generateSelfSignedCertificate(subjectName, keyPairGenerator.generateKeyPair());
		
		return ResponseEntity.ok()
				.header("content-disposition", "attachment; filename=" + subjectName + ".cer")
				.body(Base64.encode(certificate.getEncoded()));
	}
}
